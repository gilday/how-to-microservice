package com.johnathangilday.jaxrs;

import com.johnathangilday.GreetingNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps custom exceptions which do not extend {@link javax.ws.rs.WebApplicationException}
 */
@Provider
public class GreetingNotFoundExceptionMapper implements ExceptionMapper<GreetingNotFoundException> {

    @Override
    public Response toResponse(final GreetingNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .type(MediaType.TEXT_PLAIN)
                .entity("There exists no greeting with the id " + e.getId())
                .build();
    }
}
