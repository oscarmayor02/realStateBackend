package com.realEstate.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private int unreadCount;
    private LocalDateTime createdAt;  // nuevo campo

    // Añade getter y setter para createdAt

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserDTO() {}

    public UserDTO(Number id, String name, String email, Number unreadCount, LocalDateTime createdAt) {
        this.id = (id != null) ? id.longValue() : null;
        this.name = name;
        this.email = email;
        this.unreadCount = (unreadCount != null) ? unreadCount.intValue() : 0;
        this.createdAt = createdAt;

    }



    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String firstName) { this.name = firstName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}