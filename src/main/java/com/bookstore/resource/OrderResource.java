package com.bookstore.resource;

import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.CartItem;
import com.bookstore.model.Customer;
import com.bookstore.model.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTful resource class for managing customer orders.
 */
@Path("/customers/{customerId}/orders")
public class OrderResource {

    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);
    private static Map<Integer, List<Order>> ordersMap = new HashMap<>();
    private static int nextOrderId = 1;

    // Create a new order from the customer's cart
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("customerId") int customerId) {
        logger.info("POST create order for customer ID {}", customerId);
        Customer customer = validateCustomer(customerId);

        List<CartItem> cart = CartResource.getCartMap().get(customerId);
        if (cart == null || cart.isEmpty()) {
            throw new CartNotFoundException("Cart is empty or does not exist for customer " + customerId);
        }

        Order newOrder = new Order(nextOrderId++, customer.getId(), new ArrayList<>(cart), LocalDateTime.now());
        ordersMap.computeIfAbsent(customerId, k -> new ArrayList<>()).add(newOrder);

        CartResource.getCartMap().remove(customerId);
        logger.info("Order ID {} created for customer ID {}", newOrder.getOrderId(), customerId);
        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    // Retrieve all orders for a customer
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("customerId") int customerId) {
        logger.info("GET orders for customer ID {}", customerId);
        validateCustomer(customerId);
        List<Order> customerOrders = ordersMap.getOrDefault(customerId, new ArrayList<>());
        return Response.ok(customerOrders).build();
    }

    // Retrieve a specific order by ID
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        logger.info("GET order ID {} for customer ID {}", orderId, customerId);
        validateCustomer(customerId);
        List<Order> customerOrders = ordersMap.getOrDefault(customerId, new ArrayList<>());

        return customerOrders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .map(order -> Response.ok(order).build())
                .orElseThrow(()
                        -> new InvalidInputException("Order with ID " + orderId + " not found for customer " + customerId));
    }

    // Validate customer 
    private Customer validateCustomer(int customerId) {
        return CustomerResource.getAllCustomersStatic().stream()
                .filter(c -> c.getId() == customerId)
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer ID " + customerId + " not found"));
    }
}
