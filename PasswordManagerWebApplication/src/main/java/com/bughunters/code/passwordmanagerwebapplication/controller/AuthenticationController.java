package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.request.LoginRequest;
import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.EmailVerificationResponse;
import com.bughunters.code.passwordmanagerwebapplication.response.AuthorizationResponse;
import com.bughunters.code.passwordmanagerwebapplication.response.RefreshTokenResponse;
import com.bughunters.code.passwordmanagerwebapplication.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/password-manager/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthorizationResponse> registerUser(@RequestBody RegistrationRequest registrationRequest){
        log.info("User requesting to register..");
        return authenticationService.registerUser(registrationRequest);
    }

    @PutMapping("/verify/email")
    public ResponseEntity<EmailVerificationResponse> verifyEmail(
            @RequestParam ("code") Integer code,
            @RequestParam ("userId") Long userId,
            HttpServletResponse response){
        log.info("email verification request.");

        return authenticationService.verifyUserEmail(code, userId, response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponse> loginUser(@RequestBody LoginRequest loginRequest
    , HttpServletResponse response){
        log.info("User requesting to log in..");
        return authenticationService.loginUser(loginRequest, response);
    }

    @PutMapping("/refresh/token")
    public ResponseEntity<RefreshTokenResponse> refreshAccessToken(
            HttpServletRequest request, HttpServletResponse response){
        log.info("request To Refresh Access Token");

        return authenticationService.refreshToken(request, response);
    }
}
