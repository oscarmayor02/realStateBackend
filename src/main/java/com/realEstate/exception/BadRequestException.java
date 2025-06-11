package com.realEstate.exception;

// Thrown when the client sends invalid data or parameters
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}