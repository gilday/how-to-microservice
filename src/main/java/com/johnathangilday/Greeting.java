package com.johnathangilday;

import lombok.Value;

/**
 * immutable greeting object with static factory and Jackson annotations for un-marshalling
 */
@Value(staticConstructor = "of")
public class Greeting {
    public final String message;
    public final String audience;
}
