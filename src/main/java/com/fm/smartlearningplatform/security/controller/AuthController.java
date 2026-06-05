package com.fm.smartlearningplatform.security.controller;

import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.security.authenticationprovider.UserAuthenticationProvider;
import com.fm.smartlearningplatform.security.dto.AuthResponse;
import com.fm.smartlearningplatform.security.dto.DeviceInfo;
import com.fm.smartlearningplatform.security.dto.LoginRequest;
import com.fm.smartlearningplatform.security.dto.RefreshTokenRequest;
import com.fm.smartlearningplatform.security.jwt.JWTService;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitService;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitType;
import com.fm.smartlearningplatform.security.usersession.*;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final JWTService jwtService;
    private final UserSessionService userSessionService;
    private final UserAgentService userAgentService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final GeoLocationService geoLocationService;
    private final RateLimitService rateLimitService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, GeoIp2Exception {

        // Rate limitation

        String deviceId = getDeviceIdentifier(httpServletRequest);
        rateLimitService.consume(RateLimitType.LOGIN,deviceId + ":" + httpServletRequest.getRemoteAddr());

        // Authentication and retrieval

        Authentication authentication = userAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByIdAndDeletedAtIsNull(userPrincipal.id())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));


        // Device ID

        if (null != deviceId) {
            Optional<UserSession> existingSession = userSessionService.existingSessions(user.getId(), deviceId);
            if(existingSession.isPresent()){

                log.info("Existing session reused for userId: {} and deviceId: {}", user.getId(), deviceId);

                UserSession session = existingSession.get();
                String accessToken = jwtService.generateToken(user.getId());
                String refreshToken = userSessionService.createRefreshToken();
                session.setRefreshTokenHash(passwordEncoder.encode(refreshToken));
                session.setLastActiveAt(LocalDateTime.now());
                userSessionService.save(session);
                return ResponseEntity.ok(new AuthResponse(accessToken,refreshToken));
            }
        }

        deviceId = UUID.randomUUID().toString();
        log.info("New device login for userId: {}", user.getId());

        Cookie cookie = new Cookie("device_id", deviceId);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400 * 365);
        cookie.setAttribute("SameSite", "Strict");// Other option server.servlet.session.cookie.same-site=lax
        httpServletResponse.addCookie(cookie);

        // New session

        UserSession session = new UserSession();
        session.setUser(user);
        session.setDeviceIdentifier(deviceId);

        // Tokens
        String accessToken = jwtService.generateToken(userPrincipal.id());
        String refreshToken = userSessionService.createRefreshToken();
        String refreshTokenHash = passwordEncoder.encode(refreshToken);
        session.setRefreshTokenHash(refreshTokenHash);

        // User agent

        String userAgent = httpServletRequest.getHeader("User-Agent");
        DeviceInfo deviceInfo = userAgentService.parse(userAgent);
        session.setBrowserName(deviceInfo.getBrowserName());
        session.setBrowserVersion(deviceInfo.getBrowserVersion());
        session.setOsName(deviceInfo.getOsName());
        session.setOsVersion(deviceInfo.getOsVersion());
        session.setDeviceName(deviceInfo.getDeviceName());
        session.setDeviceType(deviceInfo.getDeviceType());

        // Session times

        session.setLastLoginAt(LocalDateTime.now());
        session.setLastActiveAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusDays(7));

        // Ip address and location

        String ip = getClientIp(httpServletRequest);

        try {
            GeoInfo geoInfo = geoLocationService.getGeoInfo(ip);

            session.setIpAddress(geoInfo.ip());
            session.setCountry(geoInfo.country());
            session.setCity(geoInfo.city());

        } catch (Exception ex) {
            session.setIpAddress(ip);
        }



        // Trusted
        // Have to add for if it trusted from device id which is last loged in or something like that do not remove this

//        if(request.trusted() == true){
//            boolean alreadyTrusted = userSessionRepository.existsByUserIdAndDeviceIdentifierAndTrustedTrue(user.getId(), deviceId);
//        }

        session.setTrusted(request.trusted());

        userSessionService.save(session);
        userSessionService.enforceSessionLimit(userPrincipal.id());
        return ResponseEntity.ok().body(new AuthResponse(accessToken,refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request, HttpServletRequest httpServletRequest) {

        String deviceId = getDeviceIdentifier(httpServletRequest);
        UserSession session = userSessionService.findByDeviceId(deviceId);

        rateLimitService.consume(RateLimitType.REFRESH_TOKEN, String.valueOf(session.getId()));

        if(SessionStatus.ACTIVE != session.getStatus()){
            return ResponseEntity.notFound().build();
        }

        if(!passwordEncoder.matches(request.refreshToken(),session.getRefreshTokenHash())){
            return ResponseEntity.status(401).body("Refresh token is invalid.");
        }

        String accessToken = jwtService.generateToken(session.getUser().getId());
        String refreshToken = userSessionService.createRefreshToken();
        session.setRefreshTokenHash(passwordEncoder.encode(refreshToken));
        session.setLastActiveAt(LocalDateTime.now());
        userSessionService.save(session);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request){


        String deviceId = getDeviceIdentifier(request);
        UserSession session = userSessionService.existingSessions(userPrincipal.id(), deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Session not found."));
        userSessionService.revokeSession(session);
        log.info("Logout successful for userId: {}", userPrincipal.id());
        return ResponseEntity.ok().body("Logout successfully.");
    }

    @PostMapping("/logoutAll")
    public ResponseEntity<String> logoutAll(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request){
        userSessionService.revokeAllSessions(userPrincipal.id());
        return ResponseEntity.ok().body("Logout successfully.");
    }


    // Helper

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    public String getDeviceIdentifier(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("device_id".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}