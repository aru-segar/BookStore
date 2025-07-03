package com.bookstore.resource;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.model.Customer;
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
 * RESTful resource class for managing customers.
 */
@Path("/customers")
public class CustomerResource {

    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);
    private static List<Customer> customers = new ArrayList<>();
    private static int nextId = 3;

    static {
        customers.add(new Customer(1, "Alice", "alice@example.com", "pass123"));
        customers.add(new Customer(2, "Bob", "bob@example.com", "secret"));
    }

    public static List<Customer> getAllCustomersStatic() {
        return customers;
    }

    // Create a new customer
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCustomer(Customer customer) {
        logger.info("POST add customer");
        customer.validate();

        customer.setId(nextId++);
        customers.add(customer);
        logger.info("Customer added with ID {}", customer.getId());
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    // Retrieve all customers
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        logger.info("GET all customers");
        return Response.ok(customers).build();
    }

    // Retrieve a specific customer by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") int id) {
        logger.info("GET customer ID {}", id);
        Customer customer = customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + id + " not found"));
        return Response.ok(customer).build();
    }

    // Update an existing customer by ID
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        logger.info("PUT update customer ID {}", id);
        updatedCustomer.validate();

        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                updatedCustomer.setId(id);
                customers.set(i, updatedCustomer);
                logger.info("Customer updated with ID {}", id);
                return Response.ok(updatedCustomer).build();
            }
        }

        throw new CustomerNotFoundException("Customer with ID " + id + " not found");
    }

    // Delete a customer by ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("id") int id) {
        logger.info("DELETE customer ID {}", id);
        boolean removed = customers.removeIf(c -> c.getId() == id);
        if (!removed) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found");
        }
        logger.info("Deleted customer ID {}", id);
        return Response.ok("{\"message\": \"Customer deleted successfully.\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
