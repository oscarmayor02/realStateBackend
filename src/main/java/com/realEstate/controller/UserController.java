package com.realEstate.controller;

import com.realEstate.dto.ChangePasswordRequest;
import com.realEstate.dto.RegisterRequest;
import com.realEstate.dto.UpdateProfileRequest;
import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.User;
import com.realEstate.service.AdminLogService;
import com.realEstate.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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

    @Autowired
    private AdminLogService logService;

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

    @Operation(summary = "Get user by ID")
    @GetMapping("/id/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserById(id);
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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UpdateProfileRequest user) {
        // Calls the service to update the user from the request body
        User updated = userService.updateUser(id, user);
        // Returns the updated user with HTTP 200 OK
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.ok("Password updated successfully");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteUser(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails adminUser) {

        String adminEmail = adminUser.getUsername();
        userService.deleteUser(id, adminEmail);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<java.util.Map<String, String>> changeUserRole(@PathVariable Long id, @RequestParam("role") String newRole, @AuthenticationPrincipal UserDetails adminUser) {
        userService.changeUserRole(id, newRole);
        String adminEmail = adminUser.getUsername(); // Quien hace el cambio
        logService.logAction(
                "Cambio de rol",
                adminEmail,
                "El rol del usuario con ID " + id + " fue cambiado a " + newRole
        );
        return ResponseEntity.ok(Collections.singletonMap("message", "User role updated successfully"));
    }
}