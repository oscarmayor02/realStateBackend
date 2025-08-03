package com.realEstate.repository;

import com.realEstate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Repository for performing CRUD operations on User entity
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByCedula(String cedula);
    Optional<User> findById(Long id); // ya viene con JpaRepository

}