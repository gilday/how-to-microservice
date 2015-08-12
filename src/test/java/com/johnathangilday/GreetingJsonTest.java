package com.johnathangilday;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class GreetingJsonTest {

    @Test
    public void it_creates_greetings_from_json() throws IOException {
        final String json = "{ \"message\": \"hello\", \"audience\": \"world\"}";

        final Greeting greeting = new ObjectMapper().readValue(json, Greeting.class);

        assertThat(greeting.message).isEqualTo("hello");
        assertThat(greeting.audience).isEqualTo("world");
    }
}
