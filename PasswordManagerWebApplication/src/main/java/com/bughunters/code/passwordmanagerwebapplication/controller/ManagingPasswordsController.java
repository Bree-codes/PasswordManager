
package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.service.ManagingPasswordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/password-manager/")
public class ManagingPasswordsController {

    private final ManagingPasswordsService passwordsService;
    @Autowired
    public ManagingPasswordsController(ManagingPasswordsService passwordsService) {
        this.passwordsService = passwordsService;
    }

    @PostMapping("/manage")
    public ResponseEntity<List<MappedDetailsResponse>> managedPasswords(@RequestBody List<ManagingPasswords> passwords)
    {
        log.info("request to manage passwords");
        List<MappedDetailsResponse> passwordsList = passwordsService.managePasswords(passwords);
        return ResponseEntity.status(HttpStatus.OK).body(passwordsList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ManagingPasswords>> findAll(@PathVariable long userId) throws Exception {
        List<ManagingPasswords> decrypt = passwordsService.decrypt(userId);
        return ResponseEntity.status(HttpStatus.OK).body(decrypt);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ManagingPasswords> update(@PathVariable long userId,@RequestBody ManagingPasswords passwords) throws Exception {
        ManagingPasswords managingPasswords = passwordsService.updateDetails(userId, passwords);
        return ResponseEntity.status(HttpStatus.OK).body(managingPasswords);
    }

    @DeleteMapping("/{userId}/{passwordId}")
    public ResponseEntity<ResponseEntity<String>> deletePasswordDetails(@PathVariable String passwordId, @PathVariable long userId){

        return ResponseEntity.status(HttpStatus.OK).body(passwordsService. deletePasswordByUserIdAndManaged(userId,passwordId));
    }
}

