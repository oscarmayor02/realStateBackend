package com.realEstate.controller;
import com.realEstate.dto.ConversationDTO;
import com.realEstate.model.ChatMessage;
import com.realEstate.model.Message;
import com.realEstate.model.Property;
import com.realEstate.model.User;
import com.realEstate.repository.PropertyRepository;
import com.realEstate.repository.UserRepository;
import com.realEstate.service.MessageService;
import com.realEstate.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class WebSocketMessageController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository; // <-- Agregado

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private PropertyRepository propertyRepository; // Asegúrate de tener esto


    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage) {
        User sender = userRepository.findById(chatMessage.getSenderId())
                .orElseThrow(() -> new RuntimeException("Usuario emisor no encontrado"));

        User receiver = userRepository.findById(chatMessage.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));

        Message savedMessage = new Message();
        savedMessage.setContent(chatMessage.getContent());
        savedMessage.setTimestamp(LocalDateTime.now());
        savedMessage.setSender(sender);
        savedMessage.setReceiver(receiver);
        savedMessage.setRead(false);
        Property property = null;
        if (chatMessage.getPropertyId() != null) {
            property = propertyRepository.findById(chatMessage.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
        }
        savedMessage.setProperty(property);
        System.out.println("chat" + chatMessage.getContent() + "contenido" + savedMessage );
        messageService.saveMessage(savedMessage);

        // ✅ Enviar correo HTML al receptor del mensaje
        try {
            Map<String, String> variables = Map.of(
                    "nombre", receiver.getName(),
                    "mensaje", chatMessage.getContent(),
                    "emisor", sender.getName()
            );
            String contenidoHtml = emailServiceImpl.cargarTemplate("chat-message.html", variables);

            emailServiceImpl.enviarCorreoHtml(
                    receiver.getEmail(),
                    "📨 Nuevo mensaje en UbikkApp",
                    contenidoHtml
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Enviar mensaje al receptor
        ChatMessage frontendMsg = new ChatMessage();
        frontendMsg.setSenderId(sender.getId());
        frontendMsg.setReceiverId(receiver.getId());
        frontendMsg.setContent(savedMessage.getContent());
        frontendMsg.setTimestamp(savedMessage.getTimestamp().toString());
        frontendMsg.setRead(false);

        messagingTemplate.convertAndSend(
                "/topic/messages/" + receiver.getId(),
                frontendMsg
        );

        // 🔔 Nueva lógica: enviar conversación enriquecida
        List<ConversationDTO> updatedList = messageService.getDetailedUserConversations(receiver.getId());

        ConversationDTO updatedSender = updatedList.stream()
                .filter(conv -> conv.getId().equals(sender.getId()))
                .findFirst()
                .orElse(null);

        if (updatedSender != null) {
            messagingTemplate.convertAndSend(
                    "/topic/conversations/" + receiver.getId(),
                    updatedSender
            );
        }
    }





}