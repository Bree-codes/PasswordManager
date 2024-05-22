package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException(String message) {
        super(message);
    }

}
