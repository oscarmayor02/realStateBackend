package com.realEstate.controller;
import com.realEstate.model.Message;
import com.realEstate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
// Sets the base URL path for all endpoints in this controller
@RequestMapping("/api/messages")
public class MessageController {

    // Injects the MessageService to handle business logic related to messages
    @Autowired
    private MessageService messageService;

    // Documents the endpoint for sending a message
    @Operation(summary = "Send a message")
    // Maps HTTP POST requests to /api/messages
    @PostMapping
    public Message sendMessage(@RequestBody Message message) {
        // Saves the message received in the request body and returns it
        return messageService.saveMessage(message);
    }
}