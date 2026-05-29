package com.fm.smartlearningplatform.security.service;

import com.fm.smartlearningplatform.security.SecurityConstants;
import com.fm.smartlearningplatform.security.principal.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtService {
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SecurityConstants.SECRET_KEY
                        .getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(CustomUserDetails user, String deviceId)
    {
        List<String> authorities = user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("authorities", authorities)
                .claim("deviceId", deviceId)
                .issuedAt(new Date())
                .expiration(new Date(
                                System.currentTimeMillis()
                                        + SecurityConstants.ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }
    public Claims extractClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token)
    {

        return extractClaims(token).getSubject();
    }
    public Long extractUserId(String token)
    {


        return extractClaims(token).get("userId", Long.class);
    }
    public List<String> extractAuthorities(String token)

    {
        return extractClaims(token).get("authorities", List.class);
    }
    public Date extractExpiration(String token)
    {
        return extractClaims(token).getExpiration();
    }
    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }
    public boolean isTokenValid(String token)
    {
        try {
            return !isTokenExpired(token);

        } catch (Exception e) {
            log.error("JWT validation failed", e);
            return false;
        }
    }
}