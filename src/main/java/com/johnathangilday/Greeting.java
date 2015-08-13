package com.johnathangilday;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * immutable greeting object with static factory and Jackson annotations for un-marshalling
 */
public class Greeting {

    @JsonCreator
    public static Greeting of(
            @JsonProperty("message") final String message,
            @JsonProperty("audience") final String audience) {
        return new Greeting(message, audience);
    }

    public final String message;
    public final String audience;

    private Greeting(final String message, final String audience) {
        this.message = message;
        this.audience = audience;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, audience);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Greeting other = (Greeting) obj;
        return Objects.equals(this.message, other.message)
                && Objects.equals(this.audience, other.audience);
    }
}
