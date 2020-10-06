package com.johnathangilday;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnathangilday.jaxrs.GreetingResource;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/** HK2 module for wiring Greeting components */
public class GreetingBinder extends AbstractBinder {
  @Override
  protected void configure() {
    bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class).in(Singleton.class);
    bind(GreetingResourceImpl.class).to(GreetingResource.class);
  }
}
