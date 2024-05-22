package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class UsernameNotFoundException extends RuntimeException
{
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
