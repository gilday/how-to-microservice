package com.johnathangilday;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that {@link GreetingBinder} is properly configured
 */
public class GreetingBinderTest {

    private ServiceLocator locator;

    @Before
    public void before() {
        locator = ServiceLocatorUtilities.bind(new GreetingBinder());
    }

    @Test
    public void it_binds_objectmapper_to_singleton_scope() {
        final ObjectMapper first = locator.getService(ObjectMapper.class);
        final ObjectMapper second = locator.getService(ObjectMapper.class);

        assertThat(first).isNotNull();
        assertThat(second).isNotNull();
        assertThat(first).isSameAs(second);
    }

}
