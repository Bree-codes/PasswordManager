package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class AuthenticationFailed extends RuntimeException {
    public AuthenticationFailed(String message) {
        super(message);
    }
}
