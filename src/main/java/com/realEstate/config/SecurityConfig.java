package com.realEstate.config;

import com.realEstate.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Marks this class as a Spring configuration
@Configuration
// Enables Spring Security web security
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Injects the custom JWT filter
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    // Defines the security filter chain bean using the modern lambda API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Permitir lectura pública de propiedades
                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()

                        // Propiedades
                        .requestMatchers(HttpMethod.GET, "/api/properties/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/properties/**").hasAnyRole("HOST", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/properties/**").hasAnyRole("HOST", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/properties/**").hasAnyRole("HOST", "ADMIN")

                        // Reservas
                        .requestMatchers(HttpMethod.POST, "/api/reservations/**").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/reservations/**").hasAnyRole("CLIENT", "HOST", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reservations/**").hasAnyRole("ADMIN", "HOST")

                        // Mensajes
                        .requestMatchers("/api/messages/**").hasAnyRole("CLIENT", "HOST", "ADMIN")
                        // Restricciones para usuarios
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Defines the AuthenticationManager bean, required for authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}