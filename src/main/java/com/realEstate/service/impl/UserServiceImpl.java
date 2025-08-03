package com.realEstate.service.impl;

import com.realEstate.dto.ChangePasswordRequest;
import com.realEstate.dto.RegisterRequest;
import com.realEstate.dto.UpdateProfileRequest;
import com.realEstate.exception.ConflictException;
import com.realEstate.exception.ForbiddenException;
import com.realEstate.exception.ResourceNotFoundException;
import com.realEstate.model.Role;
import com.realEstate.model.User;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.AdminLogService;
import com.realEstate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Concrete implementation of UserService
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private AdminLogService logService;

    @Autowired
    private UserRepository userRepository; // Injects the repository dependency

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailServiceImpl; // Injects the email service dependency
    // Save or update a user in the database
    @Override
    public User saveUser(RegisterRequest user) {
        Optional<User> existingUser = this.getByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new ConflictException("Email already exists: " + user.getEmail());
        }
        if (userRepository.existsByCedula(user.getCedula())) {
            throw new ConflictException("Cédula already exists: " + user.getCedula());
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
        user1.setCedula(user.getCedula());
        user1.setPassword(encryptedPasswordString);
        user1.setRole(user.getRole()); // Por defecto rol CLIENT
//        emailService.enviarCorreoHtml(
//                user1.getEmail(),
//                "¡Bienvenido a UbikkApp!",
//                "Hola " + user1.getName() + ", gracias por registrarte en nuestra plataforma."
//        );

        // Cargar el template HTML y enviar correo
        try {
            Map<String, String> variables = Map.of("nombre", user1.getName());
            String contenidoHtml = emailServiceImpl.cargarTemplate("welcome.html", variables);

            emailServiceImpl.enviarCorreoHtml(
                    user1.getEmail(),
                    "¡Bienvenido a UbikkApp!",
                    contenidoHtml
            );
        } catch (IOException e) {
            e.printStackTrace();
            // Puedes decidir si lanzar excepción o solo loguear el error aquí
        }

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
    public User updateUser(Long id, UpdateProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setCedula(request.getCedula());

        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ForbiddenException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id, String performedBy) {
        userRepository.deleteById(id);

        logService.logAction(
                "Eliminación de usuario",
                performedBy,
                "Usuario con ID " + id + " eliminado"
        );

    }

    @Override
    public void changeUserRole(Long id, String newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        Role roleEnum;
        try {
            roleEnum = Role.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConflictException("Invalid role: " + newRole);
        }

        if (roleEnum == Role.ADMIN) {
            throw new ForbiddenException("Cannot assign ADMIN role");
        }

        user.setRole(roleEnum);
        userRepository.save(user);
    }

}