package com.bookstore.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing a shopping cart in the Bookstore application. Each
 * cart is associated with a customer ID and contains a list of cart items.
 */
public class Cart {

    // Attributes
    private int customerId;
    private List<CartItem> items;

    // Default constructor to initialize the cart with an empty item list.
    public Cart() {
        this.items = new ArrayList<>();
    }

    // Parameterised constructor to initialise a cart with a specific customer ID and item list.
    public Cart(int customerId, List<CartItem> items) {
        this.customerId = customerId;
        this.items = items;
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomer(int customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
