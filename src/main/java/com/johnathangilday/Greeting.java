package com.johnathangilday;

public class Greeting {

    private final String message;
    private final String audience;

    public Greeting(String message, String audience) {
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
