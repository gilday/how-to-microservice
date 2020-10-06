package com.johnathangilday.jaxrs;

import com.johnathangilday.Greeting;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Basic JAX-RS endpoint which responds to GET requests with the JSON equivalent of hello, world.
 */
@Path("/greeting")
@Produces(MediaType.APPLICATION_JSON)
public interface GreetingResource {

  @GET
  Greeting get();

  @GET
  @Path("/{id}")
  Greeting getById(@PathParam("id") int id);
}
