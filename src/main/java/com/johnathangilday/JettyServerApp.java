package com.johnathangilday;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class JettyServerApp {

    public static void main(String[] args) {

        int port = 8080;

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler();

        ServletHolder holder = new ServletHolder(ServletContainer.class);
        holder.setInitParameter("com.sun.jersey.config.property.packages", "com.johnathangilday");

        context.addServlet(holder, "/*");

        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
