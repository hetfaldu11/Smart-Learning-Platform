package com.fm.smartlearningplatform.security.jwt;


import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class JWTGeneratorFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof UserPrincipal userPrincipal) || !authentication.isAuthenticated()) {
            return;
        }

        String jwt = jwtService.generateToken(userPrincipal.id());
        response.setHeader("Authorization", jwt);

        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !(path.equals("/login"));
    }
}