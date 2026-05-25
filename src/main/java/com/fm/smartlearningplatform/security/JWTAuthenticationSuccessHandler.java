package com.fm.smartlearningplatform.security;

import com.fm.smartlearningplatform.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        SecretKey secretKey =
                Keys.hmacShaKeyFor(
                        ApplicationConstants.SECRET_KEY
                                .getBytes(StandardCharsets.UTF_8)
                );

        String jwt =
                Jwts.builder()
                        .issuer("Smart Learning Platform")
                        .subject("JWT")
                        .claim(
                                "email",
                                authentication.getName()
                        )
                        .claim(
                                "authorities",
                                authentication.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.joining(","))
                        )
                        .issuedAt(new Date())
                        .expiration(
                                new Date(
                                        System.currentTimeMillis()
                                                + 3600000
                                )
                        )
                        .signWith(secretKey)
                        .compact();

        Cookie cookie =
                new Cookie("jwt",jwt);

        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);

        response.addCookie(cookie);

        response.sendRedirect("/secure");
    }
}