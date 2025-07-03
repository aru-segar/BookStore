package com.bookstore.model;

import com.bookstore.exception.InvalidInputException;

/**
 * Model class representing a customer in the Bookstore application. Contains
 * basic customer details like ID, name, email, and password.
 */
public class Customer {

    // Attributes
    private int id;
    private String name;
    private String email;
    private String password;

    // Default constructor required for JSON/XML marshalling.
    public Customer() {
    }

    // Parameterized constructor to initialise a customer with all necessary fields. 
    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Validate customer
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Customer name cannot be empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Customer email cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidInputException("Customer password cannot be empty.");
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
