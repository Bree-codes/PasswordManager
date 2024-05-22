package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserProfileRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ProfilesController {
    private final UserProfileService userProfileService;

    public ProfilesController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfilesFromFront profilesFromFront,
                                                         @AuthenticationPrincipal User user){
        ProfileResponse profileResponse = userProfileService.updateProfile(profilesFromFront, user);
        return ResponseEntity.status(HttpStatus.OK).body(profileResponse);

    }
}
