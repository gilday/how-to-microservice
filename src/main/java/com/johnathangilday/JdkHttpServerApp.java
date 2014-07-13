package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingApplication;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class JdkHttpServerApp {

    public static void main(String[] args) {

        int port = 8080;

        URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();

        GreetingApplication app = new GreetingApplication();

        JdkHttpServerFactory.createHttpServer(baseUri, ResourceConfig.forApplication(app));
    }
}
