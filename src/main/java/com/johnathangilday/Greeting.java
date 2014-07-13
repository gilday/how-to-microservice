package com.johnathangilday;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {

    private final String message;
    private final String audience;

    @JsonCreator
    public Greeting(
            @JsonProperty("message") String message,
            @JsonProperty("audience") String audience) {
        this.message = message;
        this.audience = audience;
    }

    public String getMessage() {
        return message;
    }

    public String getAudience() {
        return audience;
    }
}
