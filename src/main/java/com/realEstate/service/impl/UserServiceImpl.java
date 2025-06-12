package com.realEstate.service.impl;

import com.realEstate.dto.RegisterRequest;
import com.realEstate.exception.ConflictException;
import com.realEstate.exception.ForbiddenException;
import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.Role;
import com.realEstate.model.User;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

// Concrete implementation of UserService
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // Injects the repository dependency

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save or update a user in the database
    @Override
    public User saveUser(RegisterRequest user) {
        Optional<User> existingUser = this.getByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ConflictException("Email already exists: " + user.getEmail());
        }
        // Encriptar la contraseña antes de guardarla
        String encryptedPasswordString = passwordEncoder.encode(user.getPassword());
        // No permitir registrar como ADMIN por seguridad
        if (user.getRole() == Role.ADMIN) {
            throw new ForbiddenException("Cannot register as ADMIN");
        }
        // Crear nueva instancia de usuario
        User user1 = new User();
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setTelephone(user.getTelephone());
        user1.setPassword(encryptedPasswordString);
        user1.setRole(user.getRole()); // Por defecto rol CLIENT
        return userRepository.save(user1);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    // Retrieve a user by their email address
    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get all users from the database
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update an existing user's information
    @Override
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        existingUser.setName(updatedUser.getName());
        existingUser.setTelephone(updatedUser.getTelephone());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);

    }
}