package com.johnathangilday;

import com.johnathangilday.jaxrs.GreetingResource;

final class GreetingResourceImpl implements GreetingResource {

  /** @return "hello, world" JSON equivalent */
  @Override
  public Greeting get() {
    return Greeting.of("hello", "world");
  }

  /**
   * For testing exception mappers. Always fails with {@link GreetingNotFoundException}
   *
   * @param id ignored
   * @throws GreetingNotFoundException always
   */
  @Override
  public Greeting getById(final int id) {
    throw new GreetingNotFoundException(id); // no greetings ever found for this example
  }
}
