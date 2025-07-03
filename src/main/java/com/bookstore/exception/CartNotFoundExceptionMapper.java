package com.bookstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps CartNotFoundException to a 404 JSON response.
 */
@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {

    private static final Logger logger = LoggerFactory.getLogger(CartNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(CartNotFoundException exception) {
        logger.error("Cart not found: {}", exception.getMessage(), exception);

        String json = String.format(
                "{\"error\": \"Cart Not Found\", \"message\": \"%s\"}",
                exception.getMessage()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
