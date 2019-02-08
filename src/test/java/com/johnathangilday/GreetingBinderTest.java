package com.johnathangilday;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests that {@link GreetingBinder} is properly configured */
final class GreetingBinderTest {

  private ServiceLocator locator;

  @BeforeEach
  void before() {
    locator = ServiceLocatorUtilities.bind(new GreetingBinder());
  }

  @Test
  void it_binds_objectmapper_to_singleton_scope() {
    final ObjectMapper first = locator.getService(ObjectMapper.class);
    final ObjectMapper second = locator.getService(ObjectMapper.class);

    assertThat(first).isNotNull();
    assertThat(second).isNotNull();
    assertThat(first).isSameAs(second);
  }
}
