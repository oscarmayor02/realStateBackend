package com.realEstate.controller;

import com.realEstate.dto.ConversationDTO;
import com.realEstate.dto.UserDTO;
import com.realEstate.model.Message;
import com.realEstate.repository.MessageRepository;
import com.realEstate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Operation(summary = "Send a message")
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        return messageService.saveMessage(message);
    }

    @GetMapping("/history/{user1Id}/{user2Id}/{propertyId}")
    public List<Message> getChatHistory(
            @PathVariable Long user1Id,
            @PathVariable Long user2Id,
            @PathVariable Long propertyId) {
        return messageService.getChatHistory(user1Id, user2Id, propertyId).stream()
                .filter(m -> m.getProperty() != null && m.getProperty().getId().equals(propertyId))
                .peek(m -> {
                    if (m.getProperty() != null) {
                        m.setPropertyName(m.getProperty().getTitle());
                    }
                })
                .toList();
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<UserDTO>> getConversations(@PathVariable Long userId) {
        try {
            List<UserDTO> conversations = messageRepository.findDistinctContacts(userId);
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/mark-read/{senderId}/{receiverId}/{propertyId}")
    public ResponseEntity<Void> markMessagesAsRead(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @PathVariable Long propertyId) {
        messageService.markMessagesAsRead(senderId, receiverId, propertyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detailed-conversations/{userId}")
    public ResponseEntity<List<ConversationDTO>> getDetailedConversations(@PathVariable Long userId) {
        try {
            List<ConversationDTO> conversations = messageService.getDetailedUserConversations(userId);
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
