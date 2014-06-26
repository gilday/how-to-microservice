package com.johnathangilday;

public class GreetingNotFoundException extends RuntimeException {

    private final int id;

    public GreetingNotFoundException(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}
