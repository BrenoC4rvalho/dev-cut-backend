package com.breno.devcut.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("A user with the username already exists.");
    }
}
