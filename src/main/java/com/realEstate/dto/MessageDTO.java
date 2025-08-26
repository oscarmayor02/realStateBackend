package com.realEstate.dto;
import java.time.LocalDateTime;



public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private boolean read;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Long propertyId;
    private String propertyTitle;

    public MessageDTO(Long id, String content, LocalDateTime timestamp, boolean read,
                      Long senderId, String senderName,
                      Long receiverId, String receiverName,
                      Long propertyId, String propertyTitle) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.read = read;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.propertyId = propertyId;
        this.propertyTitle = propertyTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }
}