package com.bookstore.model;

import com.bookstore.exception.InvalidInputException;

/**
 * Model class representing an Author in the Bookstore application. Contains the
 * author's ID, name, and a short biography.
 */
public class Author {

    // Attributes
    private int id;
    private String name;
    private String biography;

    // Default constructor required for JSON marshalling.
    public Author() {
    }

    // Parameterized constructor to initialise an author.
    public Author(int id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    // Validate author
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Author name cannot be empty.");
        }

        if (biography == null || biography.trim().isEmpty()) {
            throw new InvalidInputException("Author biography cannot be empty.");
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
