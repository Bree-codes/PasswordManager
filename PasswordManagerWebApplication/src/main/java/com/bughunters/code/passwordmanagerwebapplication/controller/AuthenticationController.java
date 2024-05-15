package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.RegistrationResponse;
import com.bughunters.code.passwordmanagerwebapplication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/password-manager/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService1) {
        this.authenticationService = authenticationService1;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(
            @RequestBody RegistrationRequest registrationRequest, HttpServletResponse response){
        log.info("Registration Request");
        return authenticationService.registerUser(registrationRequest, response);
    }

}
