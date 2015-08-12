package com.johnathangilday;

/**
 * JAX-RS knows how to map this exception with the {@link com.johnathangilday.jaxrs.GreetingNotFoundExceptionMapper}
 */
public class GreetingNotFoundException extends RuntimeException {

    private final int id;

    public GreetingNotFoundException(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
