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

        @Query("SELECT m FROM Message m WHERE " +
               "(m.sender.id = :user1Id AND m.receiver.id = :user2Id) OR " +
               "(m.sender.id = :user2Id AND m.receiver.id = :user1Id) " +
                "AND m.propertyId = :propertyId " +
                "ORDER BY m.timestamp ASC")
        List<Message> findChatHistory(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id,
                                      @Param("propertyId") Long propertyId);

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
        @Query("UPDATE Message m SET m.read = true WHERE m.sender.id = :senderId AND m.receiver.id = :receiverId AND m.read = false AND m.propertyId = :propertyId")
        void markMessagesAsRead(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId , @Param("propertyId") Long propertyId);

        @Query(value = """
    SELECT new com.realEstate.dto.ConversationDTO(
        u.id,
        u.name,
        u.email,
        SUM(CASE WHEN m.read = false AND m.receiver_id = :userId THEN 1 ELSE 0 END),
        m.content,
        m.timestamp,
        m.property_id,
        p.title
    )
    FROM messages m
    JOIN users u ON u.id = CASE WHEN m.sender_id = :userId THEN m.receiver_id ELSE m.sender_id END
    JOIN properties p ON p.id = m.property_id
    WHERE m.id IN (
        SELECT DISTINCT ON (property_id, LEAST(sender_id, receiver_id), GREATEST(sender_id, receiver_id))
            id
        FROM messages
        WHERE sender_id = :userId OR receiver_id = :userId
        ORDER BY property_id, LEAST(sender_id, receiver_id), GREATEST(sender_id, receiver_id), timestamp DESC
    )
    GROUP BY u.id, u.name, u.email, m.property_id, p.title, m.content, m.timestamp
    ORDER BY m.timestamp DESC
    """, nativeQuery = true)
        List<ConversationDTO> findDetailedConversations(@Param("userId") Long userId);


    }
