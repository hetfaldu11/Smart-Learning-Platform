package com.fm.smartlearningplatform.security.jwt;


import com.fm.smartlearningplatform.security.principal.CustomUserDetails;
import com.fm.smartlearningplatform.security.service.JwtService;
import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;

import org.springframework.security.core.
        GrantedAuthority;

import org.springframework.security.core.authority.
        SimpleGrantedAuthority;

import org.springframework.security.core.context.
        SecurityContextHolder;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.
        OncePerRequestFilter;

import java.io.IOException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")
        ) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(7);
        if (!jwtService.isTokenValid(token))
        {
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = jwtService.extractClaims(token);
        Long userId = claims.get("userId", Long.class);
        String email = claims.getSubject();
        List<String> authoritiesFromJwt = claims.get("authorities", List.class);
        String deviceId = claims.get("deviceId", String.class);

        List<GrantedAuthority>
                authorities =
                authoritiesFromJwt
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .map(a -> (GrantedAuthority) a
                        )
                        .toList();
        CustomUserDetails principal = new CustomUserDetails(
                        userId,
                        email,
                        "",
                        deviceId,
                        authorities
                );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        authorities
                );
        SecurityContextHolder.getContext().
                setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}