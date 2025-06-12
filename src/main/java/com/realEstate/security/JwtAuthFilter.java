package com.realEstate.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// Marks this class as a Spring component so it can be auto-detected and managed
@Component
// Extends OncePerRequestFilter to ensure the filter is executed once per request
public class JwtAuthFilter extends OncePerRequestFilter {

    // Injects the JwtService to handle JWT operations
    @Autowired
    private JwtService jwtService;

    // Injects the UserDetailsService to load user-specific data
    @Autowired
    private UserDetailsService userDetailsService;

    // Main filter logic that runs for each HTTP request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Gets the value of the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // If the header is missing or doesn't start with "Bearer ", continue the filter chain and exit
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extracts the JWT token from the header (removes "Bearer " prefix)
        jwt = authHeader.substring(7);
        // Extracts the username from the JWT token
        username = jwtService.extractUsername(jwt);

        // If username is present and no authentication is set in the context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Loads user details from the database or another source
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validates the JWT token with the username
            if (jwtService.validateToken(jwt, userDetails.getUsername())) {
                // Creates an authentication token with user details and authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // Sets the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continues the filter chain
        filterChain.doFilter(request, response);
    }
}