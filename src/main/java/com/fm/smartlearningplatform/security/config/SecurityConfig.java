package com.fm.smartlearningplatform.security.config;


import com.fm.smartlearningplatform.security.authenticationprovider.UserAuthenticationProvider;
import com.fm.smartlearningplatform.security.jwt.JWTService;
import com.fm.smartlearningplatform.security.jwt.JWTValidatorFilter;
import com.maxmind.geoip2.DatabaseReader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationProvider userAuthenticationProvider,  JWTValidatorFilter jwtValidatorFilter) throws Exception {

        return http

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(userAuthenticationProvider)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/login","/refresh").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable)

                .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public DatabaseReader databaseReader() throws IOException {

        ClassPathResource resource = new ClassPathResource("geoip/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(resource.getInputStream()).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    JWTValidatorFilter jwtValidatorFilter(JWTService jwtService) {
        return new JWTValidatorFilter(jwtService);
    }
}
