package com.fm.smartlearningplatform.security.jwt;

import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import com.fm.smartlearningplatform.user.model.Authority;
import com.fm.smartlearningplatform.user.model.User;
import com.fm.smartlearningplatform.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTService {

    public final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.token.expiration.time.micro.seconds}")
    private Long jwtTokenExpirationTime;

    @Value("${password.reset.token.expiration.time.micro.seconds}")
    private Long passwordResetTokenExpirationTime;

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long id) {

        User user = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Authority> authorities = userRepository.findAuthoritiesByUserId(id);

        return Jwts.builder()
                .issuer("Smart Learning Platform")
                .subject("JWT Token")
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("authorities", authorities.stream().map(Authority::getName).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtTokenExpirationTime))
                .signWith(getSignKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserPrincipal extractUserPrincipal(Claims claims) {

        return new UserPrincipal(
                claims.get("id", Long.class),
                claims.get("email", String.class)
        );
    }


    public List<GrantedAuthority> extractAuthorities(Claims claims) {

        String authorities =
                claims.get("authorities", String.class);

        return AuthorityUtils
                .commaSeparatedStringToAuthorityList(authorities);
    }

    public boolean isTokenValid(Claims claims, User user) {

        UserPrincipal userPrincipal = extractUserPrincipal(claims);

        Long id = userPrincipal.id();
        String email = userPrincipal.email();

        return Objects.equals(id, user.getId()) && Objects.equals(email, user.getEmail());
    }


    public String generatePasswordResetToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("purpose", "PASSWORD_RESET")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + passwordResetTokenExpirationTime))
                .signWith(getSignKey())
                .compact();
    }
}