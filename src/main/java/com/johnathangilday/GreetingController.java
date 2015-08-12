package com.johnathangilday;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greeting")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingController {

    @GET
    public Greeting getGreeting() {
        return Greeting.of("hello", "world");
    }

    @GET
    @Path("/{id}")
    public Greeting getGreetingById(@PathParam("id") final int id) {
        throw new GreetingNotFoundException(id); // no greetings ever found for this example
    }
}
