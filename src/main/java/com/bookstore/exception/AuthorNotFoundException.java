package com.bookstore.exception;

/**
 * Exception thrown when an author with a specific ID is not found.
 */
public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
