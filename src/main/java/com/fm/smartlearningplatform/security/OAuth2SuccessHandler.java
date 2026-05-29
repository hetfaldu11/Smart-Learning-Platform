package com.fm.smartlearningplatform.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.core.user.OAuth2User;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        response.getWriter()
                .write("OAuth2 Success");
        OAuth2User user =
                (OAuth2User)
                        authentication.getPrincipal();

        System.out.println(user.getAttributes());

        String email =
                user.getAttribute(
                        "login"
                );

        System.out.println(
                "OAuth Login Success : "
                        + email
        );

        response.sendRedirect(
                "/secure"
        );
    }
}


//import lombok.RequiredArgsConstructor;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.Customizer;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final OAuth2SuccessHandler oAuth2SuccessHandler;
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(auth ->
//                        auth.requestMatchers( "/",
//                                        "/login",
//                                        "/oauth2/**",
//                                        "/login/oauth2/**")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated())
//                .formLogin(Customizer.withDefaults())
//                .oauth2Login(oauth ->
//                        oauth.successHandler(oAuth2SuccessHandler))
//                .build();
//    }
//    @Bean
//    InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder)
//    {
//        UserDetails user = User.builder()
//                    .username("user@gmail.com")
//                    .password(passwordEncoder.encode("12345"))
//                    .roles("USER")
//                    .build();
//        return new InMemoryUserDetailsManager(user
//        );
//    }
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories
//                .createDelegatingPasswordEncoder();
//    }
//}