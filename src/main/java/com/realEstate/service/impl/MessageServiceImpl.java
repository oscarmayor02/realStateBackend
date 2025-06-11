package com.realEstate.service.impl;

import com.realEstate.model.Message;
import com.realEstate.repository.MessageRepository;
import com.realEstate.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
