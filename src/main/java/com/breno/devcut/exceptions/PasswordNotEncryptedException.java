package com.breno.devcut.exceptions;

public class PasswordNotEncryptedException extends RuntimeException {
    public PasswordNotEncryptedException() {
        super("Password was not encrypted.");
    }
}
