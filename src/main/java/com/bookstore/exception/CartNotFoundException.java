package com.bookstore.exception;

/**
 * Exception thrown when a shopping cart for a customer is not found.
 */
public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message) {
        super(message);
    }
}
