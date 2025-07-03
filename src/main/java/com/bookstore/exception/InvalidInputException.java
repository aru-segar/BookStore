package com.bookstore.exception;

/**
 * Exception thrown when input data provided by the client is invalid.
 */
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}
