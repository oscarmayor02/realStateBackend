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

    // Sender of the message
    @ManyToOne
    private User sender;

    // Receiver of the message
    @ManyToOne
    private User receiver;

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
}