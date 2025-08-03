package com.realEstate.controller;
import com.realEstate.dto.ConversationDTO;
import com.realEstate.dto.UserDTO;
import com.realEstate.model.Message;
import com.realEstate.model.User;
import com.realEstate.repository.MessageRepository;
import com.realEstate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/messages")
public class MessageController {

    // Injects the MessageService to handle business logic related to messages
    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    // Documents the endpoint for sending a message
    @Operation(summary = "Send a message")
    // Maps HTTP POST requests to /api/messages
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // Saves the message received in the request body and returns it
        return messageService.saveMessage(message);
    }

    @GetMapping("/history/{user1Id}/{user2Id}")
    public List<Message> getChatHistory(@PathVariable Long user1Id, @PathVariable Long user2Id) {
        return messageService.getChatHistory(user1Id, user2Id);
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
    @PutMapping("/mark-read/{senderId}/{receiverId}")
    public ResponseEntity<Void> markMessagesAsRead(
            @PathVariable Long senderId,
            @PathVariable Long receiverId) {
        messageService.markMessagesAsRead(senderId, receiverId);
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