package com.fm.smartlearningplatform.security.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String clientIp = request.getRemoteAddr();
        String path     = request.getServletPath();

        RateLimitType type = resolveType(path);

        if (!rateLimitService.tryConsume(clientIp, type)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Too many requests for " + type.name() + ". Please slow down.\"}");
            return;
        }

        chain.doFilter(request, response);
    }

    // Map URL path → RateLimitType
    private RateLimitType resolveType(String path) {
        if (path.contains("/login"))       return RateLimitType.LOGIN;
        if (path.contains("/otp/send"))         return RateLimitType.OTP_SEND;
        if (path.contains("/otp/verify"))       return RateLimitType.OTP_VERIFY;
        if (path.contains("/auth/register"))    return RateLimitType.REGISTER;
        return RateLimitType.GENERAL_API;
    }
}