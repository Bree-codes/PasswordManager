package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/password")
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    @PostMapping("/change-password/{id}")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable Long id) {
        log.info("USer is requesting to change their password..");
        changePasswordService.changePassword(id, changePasswordRequest);
        return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
    }
}


