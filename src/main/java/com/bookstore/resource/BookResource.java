package com.bookstore.resource;

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTful resource class for managing books.
 */
@Path("/books")
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);
    private static List<Book> books = new ArrayList<>();
    private static int nextId = 3;

    static {
        List<Author> authors = AuthorResource.getAllAuthorsStatic();
        books.add(new Book(1, "1984", authors.get(0).getId(), "123456789", 1949, 15.99, 10));
        books.add(new Book(2, "Harry Potter", authors.get(1).getId(), "987654321", 1997, 20.99, 5));
    }

    public static List<Book> getAllBooksStatic() {
        return books;
    }

    // Create a new book
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        logger.info("POST request to add new book");

        book.validate();

        boolean authorExists = AuthorResource.getAllAuthorsStatic().stream()
                .anyMatch(a -> a.getId() == book.getAuthorId());

        if (!authorExists) {
            throw new AuthorNotFoundException("Author with ID " + book.getAuthorId() + " not found");
        }

        book.setId(nextId++);
        books.add(book);

        logger.info("Book added with ID: {}", book.getId());
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    // Retrieve all books
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        logger.info("GET all books");
        return Response.ok(books).build();
    }

    // Retrieve a specific book by its ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        logger.info("GET book ID {}", id);
        Book book = books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
        return Response.ok(book).build();
    }

    // Update an existing book
    @PUT
    @Path("/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("bookId") int bookId, Book updatedBook) {
        logger.info("PUT request to update book with ID: {}", bookId);

        updatedBook.validate();

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                boolean authorExists = AuthorResource.getAllAuthorsStatic().stream()
                        .anyMatch(a -> a.getId() == updatedBook.getAuthorId());

                if (!authorExists) {
                    throw new AuthorNotFoundException("Author with ID " + updatedBook.getAuthorId() + " not found");
                }

                updatedBook.setId(bookId);
                books.set(i, updatedBook);

                logger.info("Book updated with ID: {}", bookId);
                return Response.ok(updatedBook).build();
            }
        }

        throw new BookNotFoundException("Book with ID " + bookId + " not found for update");
    }

    // Delete a book by its ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") int id) {
        logger.info("DELETE book ID {}", id);
        boolean removed = books.removeIf(book -> book.getId() == id);
        if (!removed) {
            throw new BookNotFoundException("Book with ID " + id + " not found");
        }
        logger.info("Deleted book ID {}", id);
        return Response.ok("{\"message\": \"Book deleted successfully.\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
