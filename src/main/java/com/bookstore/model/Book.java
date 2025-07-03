package com.bookstore.model;

import com.bookstore.exception.InvalidInputException;
import java.time.Year;

/**
 * Model class representing a Book in the Bookstore application. Contains
 * attributes such as title, authorId, ISBN, publication year, price, and stock.
 */
public class Book {

    // Attributes
    private int id;
    private String title;
    private int authorId;
    private String isbn;
    private int publicationYear;
    private double price;
    private int stock;

    // Default constructor required for JSON marshalling. 
    public Book() {
    }

    // Parameterized constructor to initialize a book.
    public Book(int id, String title, int authorId, String isbn, int publicationYear, double price, int stock) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stock = stock;
    }

    // Validate book
    public void validate() {
        int currentYear = Year.now().getValue();

        if (authorId <= 0) {
            throw new InvalidInputException("Author ID must be positive.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty.");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidInputException("ISBN cannot be empty.");
        }
        if (publicationYear <= 0 || publicationYear > currentYear) {
            throw new InvalidInputException("Publication year must be valid.");
        }
        if (price <= 0) {
            throw new InvalidInputException("Price must be greater than 0.");
        }
        if (stock < 0) {
            throw new InvalidInputException("Stock cannot be negative.");
        }
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthor(int authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
