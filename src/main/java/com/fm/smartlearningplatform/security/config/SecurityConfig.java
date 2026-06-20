package com.fm.smartlearningplatform.security.config;


import com.fm.smartlearningplatform.security.authenticationprovider.UserAuthenticationProvider;
import com.fm.smartlearningplatform.security.jwt.JWTService;
import com.fm.smartlearningplatform.security.jwt.JWTValidatorFilter;
import com.fm.smartlearningplatform.security.ratelimit.RateLimitFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import java.io.IOException;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, UserAuthenticationProvider userAuthenticationProvider, JWTValidatorFilter jwtValidatorFilter, RateLimitFilter rateLimitFilter) throws Exception {

        return http

                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(userAuthenticationProvider)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/login", "/refresh").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(AbstractHttpConfigurer::disable)

                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class)

                .headers(headers -> headers
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        ) // it redirects to https instead of http so it is for prod only

                        .frameOptions(frame -> frame.deny()) // If the other site is <iframe src="https://yourwebsite.com"></iframe>
                        // It not allows that

                        .contentTypeOptions(Customizer.withDefaults())// Server sends: alert("hacked");
                        // Browser guesses:  JavaScript
                        //and executes it.
                        // Do not guess. Use exactly what server says.
                        .contentSecurityPolicy(csp ->
                                csp.policyDirectives(
                                        "default-src 'self'; " +
                                                "script-src 'self'; " +
                                                "style-src 'self'; " +
                                                "img-src 'self' data:;"
                                )
                        )
                        // Attacker injects: <script> stealCookies() </script>
                        // Without CSP: Browser executes script.
                        // With CSP: Content-Security-Policy:
                        // default-src 'self'
                        // Browser blocks it.

                        .referrerPolicy(referrer ->
                                referrer.policy(
                                        ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN
                                )
                        ) // If I go from this website to some other than in referral only, the origin goes no any path no any token only origin

                        .permissionsPolicyHeader(policy ->
                                policy.policy(
                                        "camera=(), microphone=(), geolocation=()"
                                )
                        )
                )

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
