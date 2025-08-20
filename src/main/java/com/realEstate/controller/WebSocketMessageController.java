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
    private PropertyRepository propertyRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage) {
        // ✅ Obtener sender y receiver desde BD
        User sender = userRepository.findById(chatMessage.getSenderId())
                .orElseThrow(() -> new RuntimeException("Usuario emisor no encontrado"));

        User receiver = userRepository.findById(chatMessage.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Usuario receptor no encontrado"));

        // ✅ Buscar la propiedad en la BD
        Property property = propertyRepository.findById(chatMessage.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        // ✅ Crear y guardar el mensaje en BD
        Message message = new Message();
        message.setContent(chatMessage.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setRead(false);
        message.setProperty(property);

        Message savedMessage = messageService.saveMessage(message); // <- guardado real

        // ✅ Enviar correo HTML al receptor del mensaje
        try {
            Map<String, String> variables = Map.of(
                    "nombre", receiver.getName(),
                    "mensaje", savedMessage.getContent(),
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

        // ✅ Notificar mensaje en tiempo real al receptor
        ChatMessage frontendMsg = new ChatMessage();
        frontendMsg.setSenderId(sender.getId());
        frontendMsg.setReceiverId(receiver.getId());
        frontendMsg.setContent(savedMessage.getContent());
        frontendMsg.setTimestamp(savedMessage.getTimestamp().toString());
        frontendMsg.setRead(savedMessage.isRead());
        frontendMsg.setPropertyId(property.getId());

        messagingTemplate.convertAndSend(
                "/topic/messages/" + receiver.getId(),
                frontendMsg
        );

        // ✅ Enviar conversación actualizada
        List<ConversationDTO> updatedList = messageService.getDetailedUserConversations(receiver.getId());

        ConversationDTO updatedSender = updatedList.stream()
                .filter(conv -> conv.getId().equals(sender.getId()) && conv.getPropertyId().equals(property.getId()))
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
