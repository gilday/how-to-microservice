package com.johnathangilday.jaxrs;

import com.johnathangilday.GreetingNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GreetingNotFoundExceptionMapper implements ExceptionMapper<GreetingNotFoundException> {

    @Override
    public Response toResponse(GreetingNotFoundException e) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity("There exists no greeting with the id " + e.getId())
                .build();
    }
}
