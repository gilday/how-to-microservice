package com.johnathangilday.jaxrs;

import com.johnathangilday.GreetingController;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class GreetingApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final HashSet<Class<?>> set = new HashSet<>();
        set.add(GreetingController.class);
        set.add(GreetingNotFoundExceptionMapper.class);
        set.add(ObjectMapperProvider.class);
        return set;
    }
}
