package com.realEstate.controller;
import com.realEstate.security.JwtService;
import com.realEstate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.realEstate.dto.RegisterRequest;
import com.realEstate.model.User;


// Marks this class as a Spring REST controller
@RestController
// Sets the base path for authentication requests
@RequestMapping("/api/auth")
public class AuthenticationController {

    // Injects the AuthenticationManager to authenticate users
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // Injects the service responsible for generating JWT tokens
    @Autowired
    private JwtService jwtService;

    // Handles POST requests to /api/auth/login
    @PostMapping("/login")
    public String authenticate(@RequestParam String email, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            User user = userService.getByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));            return jwtService.generateToken(user);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Operation(summary = "Register a new user", description = "Registers a new user as CLIENT or HOST.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Cedula already exists",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden role (ADMIN not allowed)",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User createdUser = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}