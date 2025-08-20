    package com.realEstate.repository;

    import com.realEstate.dto.ConversationDTO;
    import com.realEstate.dto.UserDTO;
    import com.realEstate.model.Message;
    import com.realEstate.model.User;
    import jakarta.transaction.Transactional;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;

    // Message repository for storing and retrieving chat messages
    public interface MessageRepository extends JpaRepository<Message, Long> {
        List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                Long senderId1, Long receiverId1, Long senderId2, Long receiverId2
        );

        // Historial completo de mensajes entre dos usuarios y una propiedad específica
        @Query("SELECT m FROM Message m " +
                "WHERE ((m.sender.id = :user1Id AND m.receiver.id = :user2Id) " +
                "OR (m.sender.id = :user2Id AND m.receiver.id = :user1Id)) " +
                "AND m.property.id = :propertyId " +
                "ORDER BY m.timestamp ASC")
        List<Message> findChatHistory(
                @Param("user1Id") Long user1Id,
                @Param("user2Id") Long user2Id,
                @Param("propertyId") Long propertyId
        );

        @Query("SELECT new com.realEstate.dto.UserDTO(" +
                "c.id, " +
                "c.name, " +
                "c.email, " +
                "COALESCE(SUM(CASE WHEN m.read = false AND m.receiver.id = :userId THEN 1 ELSE 0 END), 0), " +
                "c.createdAt) " +
                "FROM Message m " +
                "JOIN User c ON (c.id = CASE WHEN m.sender.id = :userId THEN m.receiver.id ELSE m.sender.id END) " +
                "WHERE m.sender.id = :userId OR m.receiver.id = :userId " +
                "GROUP BY c.id, c.name, c.email, c.createdAt")
        List<UserDTO> findDistinctContacts(@Param("userId") Long userId);

        List<Message> findBySenderIdAndReceiverIdAndReadFalse(Long senderId, Long receiverId);

        @Modifying
        @Transactional
        @Query("UPDATE Message m SET m.read = true WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId AND m.read = false AND m.property.id = :propertyId")
        void markMessagesAsRead(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("propertyId") Long propertyId);

        // --- Conversaciones detalladas con último mensaje por chat y propiedad ---
        @Query("SELECT new com.realEstate.dto.ConversationDTO(" +
                "m.sender.id, " +
                "m.sender.name, " +
                "m.sender.email, " +
                "SUM(CASE WHEN m.read = false AND m.receiver.id = :userId THEN 1 ELSE 0 END), " +
                "m.content, " +
                "m.timestamp, " +
                "m.property.id, " +
                "m.property.title) " +
                "FROM Message m " +
                "WHERE m.id IN (" +
                "   SELECT MAX(m2.id) FROM Message m2 " +
                "   WHERE m2.sender.id = :userId OR m2.receiver.id = :userId " +
                "   GROUP BY m2.property.id, " +
                "            CASE WHEN m2.sender.id = :userId THEN m2.receiver.id ELSE m2.sender.id END" +
                ") " +
                "GROUP BY m.sender.id, m.sender.name, m.sender.email, m.content, m.timestamp, m.property.id, m.property.title " +
                "ORDER BY m.timestamp DESC")
        List<ConversationDTO> findDetailedConversations(@Param("userId") Long userId);



    }
