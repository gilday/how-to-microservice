package com.johnathangilday.jaxrs;

import com.johnathangilday.GreetingController;
import com.johnathangilday.GreetingBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Use Jersey {@link ResourceConfig} to do application level component binding
 */
public class GreetingResourceConfig extends ResourceConfig {

    public GreetingResourceConfig() {
        register(new GreetingBinder());
        register(GreetingController.class);
        register(GreetingNotFoundExceptionMapper.class);
        register(ObjectMapperProvider.class);
    }
}
