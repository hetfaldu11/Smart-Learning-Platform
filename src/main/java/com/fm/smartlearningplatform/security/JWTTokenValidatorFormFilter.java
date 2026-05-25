package com.fm.smartlearningplatform.security;

import com.fm.smartlearningplatform.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFormFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] cookies =
                request.getCookies();

        for(Cookie cookie : cookies){
            if("jwt".equals(cookie.getName())){
                try{
                    SecretKey secretKey = Keys.hmacShaKeyFor(ApplicationConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

                        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(cookie.getValue()).getPayload();

                        String email = claims.get("email", String.class);

                        String authorities = claims.get("authorities", String.class);

                        Authentication auth = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                        SecurityContextHolder.getContext().setAuthentication(auth);

                    }catch(Exception ignored){
                        throw new BadCredentialsException("Token is invalid.");
                    }

                    break;
                }
            }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request) {

        return request.getServletPath()
                .equals("/login");
    }
}