package com.portfolio.portfolio_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 1. ADD THIS LINE: Enables CORS using the bean defined below
            .cors(Customizer.withDefaults()) 
            
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

    // 2. ADD THIS BEAN: This tells Spring Boot to allow your Vercel frontend to connect
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow your live Vercel frontend AND localhost (for local testing)
        configuration.setAllowedOrigins(List.of(
            "https://my-portfolio-rose-six.vercel.app",
            "http://localhost:5173" 
        ));
        
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}