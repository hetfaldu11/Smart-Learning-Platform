package com.fm.smartlearningplatform.security.jwt;


import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
import com.fm.smartlearningplatform.security.principal.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTValidatorFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (null != jwt && !jwt.startsWith("Basic ")) {
            try{
                UserPrincipal userPrincipal = jwtService.extractUserPrincipal(jwt);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal,null,
                        jwtService.extractAuthorities(jwt));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                throw new ResourceNotFoundException("Token is invalid.");
            }
        }
        filterChain.doFilter(request,response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path =  request.getServletPath();
        return (path.equals("/secure"));
    }
}