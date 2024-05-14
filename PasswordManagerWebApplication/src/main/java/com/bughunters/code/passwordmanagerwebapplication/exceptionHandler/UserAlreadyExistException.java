package com.bughunters.code.passwordmanagerwebapplication.exceptionHandler;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }

}
