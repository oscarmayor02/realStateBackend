    package com.realEstate.service.impl;

    import com.realEstate.dto.ConversationDTO;
    import com.realEstate.dto.UserDTO;
    import com.realEstate.model.Message;
    import com.realEstate.model.User;
    import com.realEstate.repository.MessageRepository;
    import com.realEstate.service.MessageService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    // Implements logic for messaging feature
    @Service
    public class MessageServiceImpl implements MessageService {

        @Autowired
        private MessageRepository messageRepository;

        // Save message to the database
        @Override
        public Message saveMessage(Message message) {
            return messageRepository.save(message);
        }

        @Override
        public List<Message> getChatHistory(Long user1Id, Long user2Id, Long propertyId) {
            return messageRepository.findChatHistory(user1Id, user2Id,  propertyId);

        }
        @Override
        public List<UserDTO> getUserConversations(Long userId) {
            return messageRepository.findDistinctContacts(userId);
        }

        @Override
        public void markMessagesAsRead(Long senderId, Long receiverId, Long propertyId) {
            messageRepository.markMessagesAsRead(senderId, receiverId, propertyId);
        }

        @Override
        public List<ConversationDTO> getDetailedUserConversations(Long userId) {
            return messageRepository.findDetailedConversations(userId);
        }

    }
