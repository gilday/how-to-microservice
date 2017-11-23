package com.johnathangilday;

/**
 * JAX-RS knows how to map this exception with the {@link
 * com.johnathangilday.jaxrs.GreetingNotFoundExceptionMapper}
 */
public class GreetingNotFoundException extends RuntimeException {

  private final int id;

  public GreetingNotFoundException(int id) {
    this.id = id;
  }

  public int id() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GreetingNotFoundException)) {
      return false;
    }
    final var that = (GreetingNotFoundException) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return id;
  }
}
