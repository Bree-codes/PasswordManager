package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.service.ManagingPasswordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/password-manager/auth")
public class ManagingPasswordsController {

    private final ManagingPasswordsService passwordsService;
    @Autowired
    public ManagingPasswordsController(ManagingPasswordsService passwordsService) {
        this.passwordsService = passwordsService;
    }

    @PostMapping("/manage")
    public ResponseEntity<List<ManagingPasswords>> managedPasswords(@RequestBody List<ManagingPasswords> passwords)
    {
        log.info("request to manage passwords");
        List<ManagingPasswords> passwordsList = passwordsService.managePasswords(passwords);
        return ResponseEntity.status(HttpStatus.OK).body(passwordsList);
    }
}
