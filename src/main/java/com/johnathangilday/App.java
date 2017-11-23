package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingResourceConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.util.Optional;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Point of entry. Configures and starts webapp */
public class App {

  public static void main(final String[] args) {
    // create config object
    final var config = config();

    // configure app
    final var app = new GreetingResourceConfig();

    // start jetty
    final var port = config.getInt("port");
    final var baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
    log.info("listening on port {}", port);
    final var server =
        JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app));
    try {

      server.join();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      try {
        server.stop();
      } catch (Exception e) {
        log.warn("exception while stopping server", e);
      }
    }
  }

  private static Config config() {
    final var defaultConfig = ConfigFactory.load();

    final var externalConfigFile = Optional.ofNullable(System.getProperty("config")).map(File::new);
    if (externalConfigFile.isPresent() && !externalConfigFile.get().exists()) {
      throw new RuntimeException(
          "external config file " + externalConfigFile.get().getAbsolutePath() + " not found");
    }

    return externalConfigFile
        .map(ConfigFactory::parseFile)
        .map(c -> c.withFallback(defaultConfig))
        .orElse(defaultConfig);
  }

  private static final Logger log = LoggerFactory.getLogger(App.class);
}
