package com.johnathangilday;

import java.util.Objects;

/** immutable greeting object with static factory and Jackson annotations for un-marshalling */
public final class Greeting {

  public static Greeting of(final String message, final String audience) {
    return new Greeting(message, audience);
  }

  private final String message;
  private final String audience;

  private Greeting(String message, String audience) {
    this.message = message;
    this.audience = audience;
  }

  public String getMessage() {
    return message;
  }

  public String getAudience() {
    return audience;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Greeting)) {
      return false;
    }
    final var greeting = (Greeting) o;
    return message.equals(greeting.message) && audience.equals(greeting.audience);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, audience);
  }
}
