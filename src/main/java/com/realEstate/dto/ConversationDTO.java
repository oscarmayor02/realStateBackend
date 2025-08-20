package com.realEstate.dto;

import java.time.LocalDateTime;

public class ConversationDTO {
    private Long id;
    private String name;
    private String email;
    private int unreadCount;
    private String lastMessage;
    private LocalDateTime timestamp;
    private Long propertyId;     // 👈 agregado
    private String propertyTitle; // 👈 opcional si quieres mostrar el nombre de la propiedad

    public ConversationDTO(Long id, String name, String email, Number unreadCount,
                           String lastMessage, LocalDateTime timestamp,
                           Long propertyId, String propertyTitle) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.unreadCount = (unreadCount != null) ? unreadCount.intValue() : 0;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
    }
    public ConversationDTO() {}

    // Getters y setters

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getUnreadCount() { return unreadCount; }
    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
