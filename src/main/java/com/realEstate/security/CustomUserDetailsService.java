package com.realEstate.security;

import com.realEstate.model.User;
import com.realEstate.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


// Marks this class as a Spring service
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Injects the user repository to access the database
    @Autowired
    private UserRepository userRepository;

    // Loads a user by username (in this case, email)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Searches for the user by email, throws exception if not found
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Returns a UserDetails object with email, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRole().getAuthorities() // Or use Collections.singletonList(new SimpleGrantedAuthority(...))
        );
    }
}