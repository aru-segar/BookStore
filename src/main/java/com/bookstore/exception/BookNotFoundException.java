package com.bookstore.exception;

/**
 * Exception thrown when a book with a specific ID is not found.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
