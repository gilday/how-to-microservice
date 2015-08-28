package com.johnathangilday;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * HK2 module for wiring Greeting components
 */
public class GreetingBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class).in(Singleton.class);
    }

}