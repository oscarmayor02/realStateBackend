package com.realEstate.model;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
// Enum representing possible user roles
public enum Role {
    CLIENT,
    HOST,
    ADMIN;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}
