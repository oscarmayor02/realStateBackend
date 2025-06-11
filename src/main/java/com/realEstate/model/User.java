package com.realEstate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
// Especifi// Marks this class as a JPA entity, mapping it to a database table
//// Specifies the table name in the database
@Table(name = "users")
public class User {

    // Unique identifier for each user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Full name of the user
    @NotBlank
    private String name;

    // Email must be unique and not blank
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String telephone;

    // Encrypted password
    @NotBlank
    private String password;

    // Role of the user: CLIENT, HOST, or ADMIN
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}