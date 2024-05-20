package com.bughunters.code.passwordmanagerwebapplication.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class AuthorizationResponse {

    private Long id;

    private HttpStatus status;

    private String message;

    private String token;

}