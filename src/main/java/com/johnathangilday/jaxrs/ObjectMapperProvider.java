package com.johnathangilday.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Registers the application {@link ObjectMapper} as the JAX-RS provider for application/json
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    @Inject
    public ObjectMapperProvider(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ObjectMapper getContext(final Class<?> objectType) {
        return mapper;
    }
}
