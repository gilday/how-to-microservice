package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingApplication;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URI;
import java.util.Optional;

/**
 * Point of entry. Configure and start webapp
 */
public class App {

    public static void main(final String[] args) {
        final Config config = config();

        final int port = config.getInt("port");
        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        final GreetingApplication app = new GreetingApplication();

        // start jetty
        final Server server = JettyHttpContainerFactory.createServer(baseUri, ResourceConfig.forApplication(app));
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Config config() {
        final Config defaultConfig = ConfigFactory.load();

        final Optional<File> externalConfigFile = Optional.ofNullable(System.getProperty("config")).map(File::new);
        if (externalConfigFile.isPresent() && !externalConfigFile.get().exists()) {
            throw new RuntimeException("external config file " + externalConfigFile.get().getAbsolutePath() + " not found");
        }

        return externalConfigFile
                .map(ConfigFactory::parseFile)
                .map(c -> c.withFallback(defaultConfig))
                .orElse(defaultConfig);
    }

}