package com.bookstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps InvalidInputException to a 400 Bad Request JSON response.
 */
@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    private static final Logger logger = LoggerFactory.getLogger(InvalidInputExceptionMapper.class);

    @Override
    public Response toResponse(InvalidInputException exception) {
        logger.error("Invalid input: {}", exception.getMessage(), exception);

        String json = String.format(
                "{\"error\": \"Invalid Input\", \"message\": \"%s\"}",
                exception.getMessage()
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
