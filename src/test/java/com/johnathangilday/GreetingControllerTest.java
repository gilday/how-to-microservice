package com.johnathangilday;

import static org.assertj.core.api.Assertions.assertThat;

import com.johnathangilday.jaxrs.GreetingResourceConfig;
import com.johnathangilday.jaxrs.ObjectMapperProvider;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Jersey Test Framework starts a new HTTP server for a given {@link Application} and provides a
 * JAX-RS client for making requests to the test server. This test verifies that the greeting
 * endpoint returns the expected JSON result.
 */
final class GreetingControllerTest extends JerseyTest {

  /** delegate to JUnit 4 setUp method in parent class */
  @BeforeEach
  void before() throws Exception {
    setUp();
  }

  /** delegate to JUnit 4 tearDown method in parent class */
  @AfterEach
  void after() throws Exception {
    tearDown();
  }

  /**
   * {@inheritDoc}
   *
   * <p>Configure the JAX-RS client with the smae Jackson configuration used by the server so that
   * it may deserialize types using the same converntions.
   */
  @Override
  protected void configureClient(ClientConfig config) {
    super.configureClient(config);
    config.register(new ObjectMapperProvider(new ObjectMapperFactory().provide()));
  }

  /**
   * {@inheritDoc}
   *
   * @return configuration containing the {@link ResourceConfig}
   */
  @Override
  protected Application configure() {
    // set the port to 0 so that it will pick next available
    // this allows us to run parallel tests
    forceSet(TestProperties.CONTAINER_PORT, "0");

    return ResourceConfig.forApplication(new GreetingResourceConfig());
  }

  /**
   * Requests a greeting from the server and verifies the JSON returned matches the expected hello,
   * world response.
   */
  @Test
  void it_returns_default_greeting() {
    final var greeting = target("greeting").request().get(Greeting.class);
    assertThat(greeting.getMessage()).isEqualTo("hello");
    assertThat(greeting.getAudience()).isEqualTo("world");
  }
}
