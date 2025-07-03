package com.bookstore.exception;

/**
 * Exception thrown when a requested book is out of stock.
 */
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super(message);
    }
}
