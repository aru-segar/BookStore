package com.bookstore.resource;

import com.bookstore.exception.AuthorNotFoundException;
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
 * RESTful resource class for managing authors.
 */
@Path("/authors")
public class AuthorResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthorResource.class);
    private static List<Author> authors = new ArrayList<>();
    private static int nextId = 3;

    static {
        authors.add(new Author(1, "George Orwell", "English novelist and critic"));
        authors.add(new Author(2, "J.K. Rowling", "British author of Harry Potter"));
    }

    public static List<Author> getAllAuthorsStatic() {
        return authors;
    }

    // Create a new author
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAuthor(Author author) {
        logger.info("POST create author");
        author.validate();

        author.setId(nextId++);
        authors.add(author);
        logger.info("Author created with ID {}", author.getId());
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    // Retrieve all authors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        logger.info("GET all authors");
        return Response.ok(authors).build();
    }

    // Retrieve a specific author by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorById(@PathParam("id") int id) {
        logger.info("GET author ID {}", id);
        Author author = authors.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));
        return Response.ok(author).build();
    }

    // Update an author
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        logger.info("PUT update author ID {}", id);
        updatedAuthor.validate();

        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId() == id) {
                updatedAuthor.setId(id);
                authors.set(i, updatedAuthor);
                logger.info("Author updated with ID {}", id);
                return Response.ok(updatedAuthor).build();
            }
        }

        throw new AuthorNotFoundException("Author with ID " + id + " not found");
    }

    // Delete an author by ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAuthor(@PathParam("id") int id) {
        logger.info("DELETE author ID {}", id);
        boolean removed = authors.removeIf(a -> a.getId() == id);
        if (!removed) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        logger.info("Deleted author ID {}", id);
        return Response.ok("{\"message\": \"Author deleted successfully.\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // Retrieve all books for a given author
    @GET
    @Path("/{id}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@PathParam("id") int authorId) {
        logger.info("GET books by author ID {}", authorId);

        authors.stream()
                .filter(a -> a.getId() == authorId)
                .findFirst()
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + authorId + " not found"));

        List<Book> books = BookResource.getAllBooksStatic().stream()
                .filter(book -> book.getAuthorId() == authorId)
                .toList();

        return Response.ok(books).build();
    }
}
