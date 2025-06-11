package com.realEstate.exception;

// Thrown for unexpected server-side errors
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}