package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingNotFoundExceptionMapper;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JdkHttpServerApp {

    public static void main(String[] args) {

        int port = 8080;

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        ResourceConfig config = new ResourceConfig(GreetingResource.class, GreetingNotFoundExceptionMapper.class);

        JdkHttpServerFactory.createHttpServer(baseUri, config);
    }
}
