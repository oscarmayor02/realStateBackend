package com.realEstate.exception;

// Thrown when there is a conflict in the application state (e.g., duplicate entries)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}