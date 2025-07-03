package com.bookstore.model;

import com.bookstore.exception.InvalidInputException;

/**
 * Model class representing an item in a customer's shopping cart. Each CartItem
 * includes a book ID and a quantity.
 */
public class CartItem {

    // Attributes
    private int bookId;
    private int quantity;

    // Default constructor required for JSON/XML marshalling.
    public CartItem() {
    }

    // Parameterised constructor to initialise a cart item with a given book ID and quantity.
    public CartItem(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Validate cart item
    public void validate() {
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBook(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
