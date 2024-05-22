package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfilesController {

    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    public ProfilesController(UserProfileService userProfileService, UserRepository userRepository) {
        this.userProfileService = userProfileService;
        this.userRepository = userRepository;
    }

    @PutMapping("/profiles")
    public ResponseEntity<ProfileResponse> updateProfile(@RequestBody ProfilesFromFront profilesFromFront,
                                                         @AuthenticationPrincipal User user) {
        ProfileResponse profileResponse = userProfileService.updateProfile(profilesFromFront, user);
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/profiles/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        ProfileResponse userProfile = userProfileService.getUserProfile(user);
        return ResponseEntity.ok(userProfile);
    }

    @DeleteMapping("/profiles/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        userProfileService.deleteUserProfile(user);
        return ResponseEntity.ok("Profile deleted successfully");
    }
}


