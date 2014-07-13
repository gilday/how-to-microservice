package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingApplication;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.assertj.core.api.Assertions.assertThat;

public class GreetingControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        // set the port to 0 so that it will pick next available
        // this allows us to run parallel tests
        forceSet(TestProperties.CONTAINER_PORT, "0");

        return ResourceConfig.forApplication(new GreetingApplication());
    }

    @Test
    public void it_returns_default_greeting() {
        final Greeting greeting = target("greeting").request().get(Greeting.class);
        assertThat(greeting.getMessage()).isEqualTo("hello");
        assertThat(greeting.getAudience()).isEqualTo("world");
    }
}
