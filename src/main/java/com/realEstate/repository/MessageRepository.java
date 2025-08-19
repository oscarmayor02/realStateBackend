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
        @Query(value = """
    SELECT 
        CASE WHEN m.sender_id = :userId THEN m.receiver_id ELSE m.sender_id END as id,
        CASE WHEN m.sender_id = :userId THEN u_receiver.name ELSE u_sender.name END as name,
        CASE WHEN m.sender_id = :userId THEN u_receiver.email ELSE u_sender.email END as email,
        SUM(CASE WHEN m.read = false AND m.receiver_id = :userId THEN 1 ELSE 0 END) as unreadCount,
        m.content as lastMessage,
        m.timestamp as timestamp,
        m.property_id as propertyId,
        p.title as propertyTitle
    FROM messages m
    JOIN users u_sender ON u_sender.id = m.sender_id
    JOIN users u_receiver ON u_receiver.id = m.receiver_id
    JOIN properties p ON p.id = m.property_id
    WHERE m.sender_id = :userId OR m.receiver_id = :userId
    GROUP BY id, name, email, m.content, m.timestamp, m.property_id, p.title
    ORDER BY m.timestamp DESC
""", nativeQuery = true)
        List<ConversationDTO> findDetailedConversations(@Param("userId") Long userId);


    }
