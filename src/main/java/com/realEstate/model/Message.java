package com.realEstate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    // Unique ID for the message
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Message content
    private String content;

    // Date and time of the message
    private LocalDateTime timestamp;

    @Column(name = "read", nullable = false)
    private boolean read = false;


    // Sender of the message
    @ManyToOne
    private User sender;

    // Receiver of the message
    @ManyToOne
    private User receiver;
    private Long propertyId; // 👈 nuevo campo

    // Getters y Setters


    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}