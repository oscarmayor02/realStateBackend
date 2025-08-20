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
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @Autowired
    private PropertyRepository propertyRepository;

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage) {
        try {
            Long senderId = Long.valueOf(chatMessage.getSenderId());
            Long receiverId = Long.valueOf(chatMessage.getReceiverId());
            Long propertyId = chatMessage.getPropertyId() != null ? Long.valueOf(chatMessage.getPropertyId()) : null;

            User sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new RuntimeException("Usuario emisor no encontrado"));

            User receiver = userRepository.findById(receiverId)
                    .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));

            Property property = null;
            if (propertyId != null) {
                property = propertyRepository.findById(propertyId)
                        .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));
            }

            // ✅ Crear entidad Message y guardar
            Message savedMessage = new Message();
            savedMessage.setSender(sender);
            savedMessage.setReceiver(receiver);
            savedMessage.setProperty(property);
            savedMessage.setContent(chatMessage.getContent());
            savedMessage.setTimestamp(LocalDateTime.now());
            savedMessage.setRead(false);

            savedMessage = messageService.saveMessage(savedMessage);
            System.out.println("Mensaje guardado con ID: " + savedMessage.getId());

            // ✅ Enviar correo al receptor
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

            // ✅ Enviar mensaje al front vía WebSocket
            ChatMessage frontendMsg = new ChatMessage();
            frontendMsg.setSenderId(senderId);
            frontendMsg.setReceiverId(receiverId);
            frontendMsg.setContent(savedMessage.getContent());
            frontendMsg.setTimestamp(savedMessage.getTimestamp().toString());
            frontendMsg.setRead(false);
            frontendMsg.setPropertyId(propertyId);
            System.out.println("Enviando mensaje al front: " + frontendMsg);
            messagingTemplate.convertAndSend(
                    "/topic/messages/" + receiverId,
                    frontendMsg
            );

            // ✅ Enviar conversación actualizada
            List<ConversationDTO> updatedList = messageService.getDetailedUserConversations(receiverId);

            ConversationDTO updatedSender = updatedList.stream()
                    .filter(conv -> conv.getId().equals(senderId))
                    .findFirst()
                    .orElse(null);

            if (updatedSender != null) {
                messagingTemplate.convertAndSend(
                        "/topic/conversations/" + receiverId,
                        updatedSender
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
