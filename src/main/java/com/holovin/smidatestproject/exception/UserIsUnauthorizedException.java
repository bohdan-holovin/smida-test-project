package com.holovin.smidatestproject.exception;

public class UserIsUnauthorizedException extends RuntimeException {
    public UserIsUnauthorizedException(String username) {
        super("User is unauthorized with username: " + username);
    }
}
