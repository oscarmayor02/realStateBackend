package com.realEstate.service;

import com.realEstate.dto.ConversationDTO;
import com.realEstate.dto.UserDTO;
import com.realEstate.model.Message;
import com.realEstate.model.User;

import java.util.List;

// Interface for messaging functionality between users
public interface MessageService {
    Message saveMessage(Message message); // Save chat or contact message
    List<Message> getChatHistory(Long user1Id, Long user2Id);
    List<UserDTO> getUserConversations(Long userId);
    void markMessagesAsRead(Long senderId, Long receiverId);
    List<ConversationDTO> getDetailedUserConversations(Long userId);

}
