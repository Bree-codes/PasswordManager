package com.bughunters.code.passwordmanagerwebapplication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private HttpStatus status;

    private String message;

}