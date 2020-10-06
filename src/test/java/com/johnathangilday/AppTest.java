package com.johnathangilday;

import static org.assertj.core.api.Assertions.assertThat;

import com.johnathangilday.jaxrs.GreetingResource;
import com.johnathangilday.jaxrs.ObjectMapperProvider;
import com.typesafe.config.ConfigFactory;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Smoke test for {@link App} */
final class AppTest {

  private App app;

  @BeforeEach
  void before() throws Exception {
    final var config = ConfigFactory.parseMap(Map.of("port", 0));
    app = new App(config);
    app.start();
  }

  @Test
  void app_start_smoke_test() {
    final var mapper = new ObjectMapperFactory().provide();
    final var client =
        ClientBuilder.newBuilder().register(new ObjectMapperProvider(mapper)).build();
    final var target = client.target(app.uri());
    final var greetings = WebResourceFactory.newResource(GreetingResource.class, target);
    final var greeting = greetings.get();
    assertThat(greeting).isEqualTo(Greeting.of("hello", "world"));
  }

  @AfterEach
  void after() throws Exception {
    app.stop();
  }
}
