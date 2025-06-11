package com.realEstate.repository;

import com.realEstate.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

// Message repository for storing and retrieving chat messages
public interface MessageRepository extends JpaRepository<Message, Long> {
}