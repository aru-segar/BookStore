package com.bookstore.exception;

/**
 * Exception thrown when a customer with a specific ID is not found.
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
