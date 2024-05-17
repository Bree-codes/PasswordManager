package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException {
    public IncorrectVerificationCodeException(String message) {
        super(message);
    }
}
