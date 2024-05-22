package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.service.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    @PostMapping("/change-password/{Id}")
    public ResponseEntity<String> changePassword(@PathVariable Long id, ChangePasswordRequest changePasswordRequest, @PathVariable String Id) {
        changePasswordService.changePassword(id, changePasswordRequest);
        return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
    }
}


