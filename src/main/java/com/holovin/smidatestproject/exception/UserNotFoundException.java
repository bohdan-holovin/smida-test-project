package com.holovin.smidatestproject.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String details) {
        super("User not found with details: " + details);
    }
}
