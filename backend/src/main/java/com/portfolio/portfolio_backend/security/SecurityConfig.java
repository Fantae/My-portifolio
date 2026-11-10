package com.portfolio.portfolio_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            )

            .authorizeHttpRequests(auth -> auth

                // H2 Console
                .requestMatchers("/h2-console/**").permitAll()

                // Authentication
                .requestMatchers("/api/auth/**").permitAll()

                // Public Portfolio APIs
                .requestMatchers(
                    "/api/projects/**",
                    "/api/profile/**",
                    "/api/skills/**",
                    "/api/experience/**",
                    "/api/education/**"
                ).permitAll()

                // Admin APIs
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                // Everything else requires login
                .anyRequest()
                .authenticated()
            );

        return http.build();
    }
}