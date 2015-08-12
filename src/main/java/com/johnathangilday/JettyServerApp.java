package com.johnathangilday;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServerApp {

    public static void main(final String[] args) {

        final int port = 8080;

        final Server server = new Server(port);
        final ServletContextHandler context = new ServletContextHandler();

        final ServletHolder holder = new ServletHolder(ServletContainer.class);
        holder.setInitParameter("com.sun.jersey.config.property.packages", "com.johnathangilday");

        context.addServlet(holder, "/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
