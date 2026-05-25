package com.fm.smartlearningplatform.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationSuccessHandler successHandler;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .oauth2Login(olc -> olc
                        .successHandler(successHandler))

                .addFilterBefore(
                        new JWTTokenValidatorFormFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                .addFilterBefore(
                        new JWTTokenValidatorFilter(),
                        BasicAuthenticationFilter.class
                )

                .addFilterAfter(
                        new JWTTokenGeneratorFilter(),
                        BasicAuthenticationFilter.class
                )

                .formLogin(form -> form
                        .successHandler(successHandler)
                )

                .httpBasic(Customizer.withDefaults())

                .logout(logout -> logout
                        .deleteCookies("jwt","JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )

                .build();
    }

    @Bean
    UserDetailsManager inMemoryUserDetailManager(PasswordEncoder passwordEncoder){
        UserDetails user1 = User.builder()
                .username("user1@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .authorities("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("user2@gmail.com")
                .password(passwordEncoder.encode("12345"))
                .authorities("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    InMemoryClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(githubClient(),googleClient());
    }

    private ClientRegistration githubClient() {
        return CommonOAuth2Provider.GITHUB
                .getBuilder("github")
                .clientId("Ov23liQqhIQ3JSRf15yf")
                .clientSecret("3a65d9bd0aeaa25f3d3ea39a614130b4c44eeb1e")
                .build();
    }

    private ClientRegistration googleClient() {
        return CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId("133456977580-atfelm2fg6t8q4j5nu5737tfqb8lkgri.apps.googleusercontent.com")
                .clientSecret("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")
                .build();
    }
}