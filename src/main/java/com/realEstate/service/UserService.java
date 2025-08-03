package com.realEstate.service;

import com.realEstate.dto.ChangePasswordRequest;
import com.realEstate.dto.RegisterRequest;
import com.realEstate.dto.UpdateProfileRequest;
import com.realEstate.model.User;
import java.util.List;
import java.util.Optional;
// Interface defining the contract for user-related operations
public interface UserService {
    User saveUser(RegisterRequest user); // Save or update a user
    Optional<User> getByEmail(String email); // Find user by email
    List<User> getAllUsers(); // List all users
    User getUserById(Long id);

   // Optional<User> findByname(String name);
   void changePassword(Long id, ChangePasswordRequest request);

    User updateUser(Long id, UpdateProfileRequest UpdateProfileRequest);
    void deleteUser(Long id, String performedBy); // Delete a user by ID
    void changeUserRole(Long id, String newRole);

}
