package com.realEstate.exception;


// Thrown when a user tries to access a resource without being authenticated
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}