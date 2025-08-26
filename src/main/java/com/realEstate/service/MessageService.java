package com.realEstate.service;

import com.realEstate.dto.ConversationDTO;
import com.realEstate.dto.UserDTO;
import com.realEstate.model.Message;
import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);
    List<Message> getChatHistory(Long user1Id, Long user2Id, Long propertyId);
    List<UserDTO> getUserConversations(Long userId);
    void markMessagesAsRead(Long senderId, Long receiverId, Long propertyId);
    List<ConversationDTO> getDetailedUserConversations(Long userId);
}
