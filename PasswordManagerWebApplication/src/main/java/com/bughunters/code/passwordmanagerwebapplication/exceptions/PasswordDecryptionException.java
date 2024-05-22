package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class PasswordDecryptionException extends RuntimeException{

    public PasswordDecryptionException(String message){
        super(message);
    }
}
