package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.AuthorizationResponse;
import com.bughunters.code.passwordmanagerwebapplication.service.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;
    private final UserRepository userRepository;

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        log.info("User is requesting to change password");
        return ResponseEntity.ok("Password changed successfully!");
    }
}
