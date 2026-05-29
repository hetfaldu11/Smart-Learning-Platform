package com.fm.smartlearningplatform.controller.user;

import com.fm.smartlearningplatform.dto.user.login.LoginRequest;
import com.fm.smartlearningplatform.dto.user.login.LoginResponse;
import com.fm.smartlearningplatform.dto.user.login.RefreshTokenRequest;
import com.fm.smartlearningplatform.dto.user.login.TokenResponse;
import com.fm.smartlearningplatform.model.user.RefreshToken;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.repository.user.UserRepository;
import com.fm.smartlearningplatform.security.principal.CustomUserDetails;
import com.fm.smartlearningplatform.security.service.JwtService;

import com.fm.smartlearningplatform.security.service.RefreshTokenService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request)
    {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        User userEntity = userRepository.findByEmailAndDeletedAtIsNull(request.getEmail())
                        .orElseThrow();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userEntity);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(user, user.getDeviceId());
        return new LoginResponse(accessToken, refreshToken.getToken());
    }
    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody RefreshTokenRequest request)
    {
        RefreshToken refreshToken = refreshTokenService.validateToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        List<? extends  GrantedAuthority> authorities =
                user.getUserRoles()
                        .stream()
                        .map(ur ->
                                        new SimpleGrantedAuthority(ur.getRole().getName())
                        )
                        .toList();
        CustomUserDetails principal =
                new CustomUserDetails(
                        user.getId(),
                        user.getEmail(),
                        "",
                        null,
                        authorities
                );
        String accessToken = jwtService.generateToken(principal, principal.getDeviceId());
        RefreshToken newRefreshToken = refreshTokenService.rotateToken(refreshToken);
        return new TokenResponse(accessToken, newRefreshToken.getToken());
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication)
    {
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();
        refreshTokenService.revokeAllTokens(user.getId());
    }
}