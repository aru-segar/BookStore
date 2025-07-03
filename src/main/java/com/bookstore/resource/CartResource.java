package com.bookstore.resource;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * RESTful resource class for managing a customer's shopping cart.
 */
@Path("/customers/{customerId}/cart")
public class CartResource {

    private static final Logger logger = LoggerFactory.getLogger(CartResource.class);
    private static Map<Integer, List<CartItem>> carts = new HashMap<>();

    // Add an item to the customer's cart
    @POST
    @Path("/items")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(@PathParam("customerId") int customerId, CartItem cartItem) {
        logger.info("POST add to cart for customer ID {}", customerId);
        validateCustomer(customerId);

        cartItem.validate();

        Book book = getBook(cartItem.getBookId());

        if (cartItem.getQuantity() > book.getStock()) {
            throw new OutOfStockException("Not enough stock for book: " + book.getTitle());
        }

        List<CartItem> cart = carts.computeIfAbsent(customerId, k -> new ArrayList<>());

        for (CartItem item : cart) {
            if (item.getBookId() == book.getId()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                logger.info("Updated quantity for book in cart: {}", book.getTitle());
                return Response.ok(item).build();
            }
        }

        cart.add(cartItem);
        logger.info("Added book '{}' to cart", book.getTitle());
        return Response.status(Response.Status.CREATED).entity(cartItem).build();
    }

    // Retrieve customer's cart
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("customerId") int customerId) {
        logger.info("GET cart for customer ID {}", customerId);
        validateCustomer(customerId);
        List<CartItem> cart = carts.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(cart).build();
    }

    // Update quantity of a specific item in the cart
    @PUT
    @Path("/items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCartItem(@PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId,
            CartItem updatedItem) {
        logger.info("PUT cart item for customer ID {} and book ID {}", customerId, bookId);
        validateCustomer(customerId);

        if (updatedItem.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        getBook(bookId);

        List<CartItem> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer " + customerId);
        }

        for (CartItem item : cart) {
            if (item.getBookId() == bookId) {
                item.setQuantity(updatedItem.getQuantity());
                logger.info("Updated quantity for book ID {}", bookId);
                return Response.ok(item).build();
            }
        }

        throw new BookNotFoundException("Book not found in cart with ID " + bookId);
    }

    // Remove a specific item from the cart
    @DELETE
    @Path("/items/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCartItem(@PathParam("customerId") int customerId,
            @PathParam("bookId") int bookId) {
        logger.info("DELETE cart item for customer ID {} and book ID {}", customerId, bookId);
        validateCustomer(customerId);
        List<CartItem> cart = carts.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for customer " + customerId);
        }

        boolean removed = cart.removeIf(item -> item.getBookId() == bookId);
        if (!removed) {
            throw new BookNotFoundException("Book not found in cart: ID " + bookId);
        }

        logger.info("Removed book ID {} from cart", bookId);
        return Response.ok("{\"message\": \"Book removed from cart successfully.\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    // Check if customer exists
    private void validateCustomer(int customerId) {
        CustomerResource.getAllCustomersStatic().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer ID " + customerId + " not found"));
    }

    // Get book by ID or throw exception
    private Book getBook(int bookId) {
        return BookResource.getAllBooksStatic().stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book ID " + bookId + " not found"));
    }

    public static Map<Integer, List<CartItem>> getCartMap() {
        return carts;
    }
}
