package com.realEstate.service;

import com.realEstate.dto.RegisterRequest;
import com.realEstate.model.User;
import java.util.List;
import java.util.Optional;
// Interface defining the contract for user-related operations
public interface UserService {
    User saveUser(RegisterRequest user); // Save or update a user
    Optional<User> getByEmail(String email); // Find user by email
    List<User> getAllUsers(); // List all users
    User getUserById(Long id);
    /**
     * Update user details by their ID.
     * Throws ResourceNotFoundException if the user is not found.
     *
     * @param id ID of the user to update
     * @param updatedUser Updated user data
     * @return Updated User entity
     */
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id); // Delete a user by ID
}
