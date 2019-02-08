package com.johnathangilday;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ObjectMapper} configuration to verify that it can de-serliaze an object of type
 * {@link Greeting} from JSON.
 */
final class GreetingJsonTest {

  @Test
  void it_creates_greetings_from_json() throws IOException {
    final var json = "{ \"message\": \"hello\", \"audience\": \"world\"}";

    final var mapper = new ObjectMapperFactory().provide();
    final var greeting = mapper.readValue(json, Greeting.class);

    assertThat(greeting.getMessage()).isEqualTo("hello");
    assertThat(greeting.getAudience()).isEqualTo("world");
  }
}
