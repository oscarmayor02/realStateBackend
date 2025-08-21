package com.realEstate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime timestamp;
    @Transient
    private String propertyName;

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }
    @Column(name = "read", nullable = false)
    private boolean read = false;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties({"messagesSent", "messagesReceived", "password"}) // Ajusta según tu User
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties({"messagesSent", "messagesReceived", "password"})
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonIgnoreProperties({"messages", "host"})
    private Property property;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }
}
