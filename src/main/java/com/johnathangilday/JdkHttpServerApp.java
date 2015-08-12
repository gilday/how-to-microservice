package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingApplication;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JdkHttpServerApp {

    public static void main(final String[] args) {

        final int port = 8080;

        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        final GreetingApplication app = new GreetingApplication();

        JdkHttpServerFactory.createHttpServer(baseUri, ResourceConfig.forApplication(app));
    }
}
