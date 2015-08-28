package com.johnathangilday;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.Factory;

/**
 * create and configure a Jackson {@link ObjectMapper} for use throughout the application
 */
public class ObjectMapperFactory implements Factory<ObjectMapper> {

    @Override
    public ObjectMapper provide() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void dispose(final ObjectMapper instance) {

    }
}
