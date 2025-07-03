package com.bookstore.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maps AuthorNotFoundException to a 404 JSON response.
 */
@Provider
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException> {

    private static final Logger logger = LoggerFactory.getLogger(AuthorNotFoundExceptionMapper.class);

    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        logger.error("Author not found: {}", exception.getMessage(), exception);

        String json = String.format(
                "{\"error\": \"Author Not Found\", \"message\": \"%s\"}",
                exception.getMessage()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
