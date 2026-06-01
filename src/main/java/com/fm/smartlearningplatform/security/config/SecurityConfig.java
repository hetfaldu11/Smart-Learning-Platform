package com.fm.smartlearningplatform.security.config;


import com.fm.smartlearningplatform.security.authenticationprovider.UserAuthenticationProvider;
import com.fm.smartlearningplatform.security.jwt.JWTGeneratorFilter;
import com.fm.smartlearningplatform.security.jwt.JWTValidatorFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationProvider userAuthenticationProvider, JWTGeneratorFilter jwtGeneratorFilter, JWTValidatorFilter jwtValidatorFilter) throws Exception {

        return http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )

                .authenticationProvider(userAuthenticationProvider)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/api/v1/users/me").authenticated()
                        .requestMatchers("/api/v1/users/preferences/**").authenticated()
                        .requestMatchers("/api/v1/users/profile/**").authenticated()
                        .requestMatchers("/api/v1/users/social-links/**").authenticated()
                        .requestMatchers("/api/v1/user-skills/**").authenticated()
                        .anyRequest().authenticated()
                )

//                .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class)
//
//                .addFilterAfter(jwtGeneratorFilter, BasicAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
