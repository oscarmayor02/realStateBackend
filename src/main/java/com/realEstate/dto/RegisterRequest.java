package com.realEstate.dto;

import com.realEstate.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// DTO used for user registration
public class RegisterRequest {

    // Full name of the user (required)
    @NotBlank(message = "Full name is required")
    private String name;

    // Email of the user (must be unique and valid)
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    // Phone number
    @NotBlank(message = "Phone number is required")
    private String telephone;

    // Plaintext password (will be encrypted before saving)
    @NotBlank(message = "Password is required")
    private String password;

    // Role selected by the user during registration (CLIENT or HOST only)
    @NotNull(message = "Role is required")
    private Role role;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
