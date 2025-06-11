package com.realEstate.service;

import com.realEstate.model.Message;

// Interface for messaging functionality between users
public interface MessageService {
    Message saveMessage(Message message); // Save chat or contact message
}
