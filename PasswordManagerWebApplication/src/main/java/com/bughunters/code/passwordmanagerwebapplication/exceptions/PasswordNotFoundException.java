package com.bughunters.code.passwordmanagerwebapplication.exceptions;

public class PasswordNotFoundException extends RuntimeException
{

     public PasswordNotFoundException(String message){
         super(message);
     }

}
