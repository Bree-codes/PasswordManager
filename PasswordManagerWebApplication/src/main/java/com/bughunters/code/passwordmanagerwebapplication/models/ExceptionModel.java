package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class ExceptionModel {

    private String message;

    private Date date;
}