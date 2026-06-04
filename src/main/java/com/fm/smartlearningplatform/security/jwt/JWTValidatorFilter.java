package com.fm.smartlearningplatform.security.jwt;


import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTValidatorFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (null != jwt && !jwt.startsWith("Basic ")) {
            try{
                Claims claims = jwtService.extractClaims(jwt);
                UserPrincipal userPrincipal = jwtService.extractUserPrincipal(claims);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal,null, jwtService.extractAuthorities(claims));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (JwtException e){
                SecurityContextHolder.clearContext();
                throw new BadCredentialsException("Invalid JWT");
            }
        }
        filterChain.doFilter(request,response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path =  request.getServletPath();
        return (path.equals("/login"));
    }
}