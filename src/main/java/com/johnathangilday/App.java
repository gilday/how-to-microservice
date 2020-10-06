package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingResourceConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Point of entry. Configures and starts webapp */
public class App {

  public static void main(final String[] args) {
    // build config
    final var defaultConfig = ConfigFactory.load();
    final var externalConfigFile = Optional.ofNullable(System.getProperty("config")).map(File::new);
    externalConfigFile.ifPresent(
        file -> {
          if (!file.exists()) {
            logger.error("external config file {} not found", file);
            System.exit(1);
          }
        });
    final var config =
        externalConfigFile
            .map(ConfigFactory::parseFile)
            .map(c -> c.withFallback(defaultConfig))
            .orElse(defaultConfig);

    // build and start app
    final var app = new App(config);
    try {
      app.start();
    } catch (Exception e) {
      logger.error("Failed to start server", e);
    }
    if (logger.isInfoEnabled()) {
      logger.info("listening at {}", app.uri());
    }
    try {
      app.server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.error("Interrupted", e);
    }
  }

  private final Server server;

  public App(Config config) {
    // configure server
    final var app = new GreetingResourceConfig();

    // start jetty
    final var port = config.getInt("port");
    final var baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
    server =
        JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app), false);
  }

  /**
   * @throws IllegalStateException when server has already been started
   * @throws Exception when server fails to start
   */
  public void start() throws Exception {
    if (server.isStarted()) {
      throw new IllegalStateException("Server already started");
    }
    server.start();
  }

  /**
   * @throws IllegalStateException when server has not yet been started
   * @throws Exception when server fails to stop
   */
  public void stop() throws Exception {
    if (!server.isStarted()) {
      throw new IllegalStateException("Must first start the server");
    }
    server.stop();
  }

  /**
   * @throws IllegalStateException when server has not yet been started
   * @return the URI with hostname and port that the server is bound to
   */
  public URI uri() {
    if (!server.isStarted()) {
      throw new IllegalStateException("Must first start the server");
    }
    final var connector = (ServerConnector) server.getConnectors()[0];
    final var port = connector.getLocalPort();
    final var host = InetAddress.getLoopbackAddress().getHostName();
    try {
      return new URI("http", null, host, port, "/", null, null);
    } catch (URISyntaxException e) {
      throw new AssertionError("Failed to build URI from connector - this should never happen", e);
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(App.class);
}
