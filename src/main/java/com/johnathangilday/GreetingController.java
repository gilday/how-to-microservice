package com.johnathangilday;

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
public class GreetingController {

  /** @return "hello, world" JSON equivalent */
  @GET
  public Greeting getGreeting() {
    return Greeting.of("hello", "world");
  }

  /**
   * For testing exception mappers. Always fails with {@link GreetingNotFoundException}
   *
   * @param id ignored
   * @throws GreetingNotFoundException always
   */
  @GET
  @Path("/{id}")
  public Greeting getGreetingById(@PathParam("id") final int id) {
    throw new GreetingNotFoundException(id); // no greetings ever found for this example
  }
}
