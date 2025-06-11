package com.realEstate.exception;

// Thrown when a user is authenticated but not authorized to perform an action
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}