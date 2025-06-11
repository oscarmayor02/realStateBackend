package com.realEstate.repository;

import com.realEstate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository for performing CRUD operations on User entity
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}