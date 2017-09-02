package com.johnathangilday;

import lombok.AllArgsConstructor;

/**
 * JAX-RS knows how to map this exception with the {@link com.johnathangilday.jaxrs.GreetingNotFoundExceptionMapper}
 */
@AllArgsConstructor
public class GreetingNotFoundException extends RuntimeException {
    public final int id;
}
