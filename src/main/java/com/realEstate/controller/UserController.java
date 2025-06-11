package com.realEstate.controller;

import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.User;
import com.realEstate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// Swagger annotation to document the controller with the name "Users" and a description
@Tag(name = "Users", description = "Operations related to users")
// Marks this class as a Spring REST controller
@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/users")
public class UserController {

    // Injects the UserService to handle business logic
    @Autowired
    private UserService userService;

    // Documents the endpoint for creating a new user
    @Operation(summary = "Create a new user")
    // Maps HTTP POST requests to /api/users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Calls the service to save the user from the request body
        User savedUser = userService.saveUser(user);
        // Returns the saved user with HTTP 200 OK
        return ResponseEntity.ok(savedUser);
    }

    // Documents the endpoint for retrieving all users
    @Operation(summary = "Get all users")
    // Maps HTTP GET requests to /api/users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // Calls the service to get the list of all users
        List<User> users = userService.getAllUsers();
        // Returns the list of users with HTTP 200 OK
        return ResponseEntity.ok(users);
    }

    // Documents the endpoint for retrieving a user by email
    @Operation(summary = "Get user by email")
    // Maps HTTP GET requests to /api/users/{email}
    @GetMapping("/{email}")
    public User getByEmail(@PathVariable String email) {
        // Returns the user found by email, or throws an exception if not found
        return userService.getByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    // Documents the endpoint for updating a user
    @Operation(summary = "Update user")
    // Maps HTTP PUT requests to /api/users
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Calls the service to update the user from the request body
        User updated = userService.updateUser(id,user);
        // Returns the updated user with HTTP 200 OK
        return ResponseEntity.ok(updated);
    }
}