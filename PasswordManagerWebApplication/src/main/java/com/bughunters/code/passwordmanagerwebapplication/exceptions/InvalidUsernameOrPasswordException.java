package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class InvalidUsernameOrPasswordException extends RuntimeException {

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
