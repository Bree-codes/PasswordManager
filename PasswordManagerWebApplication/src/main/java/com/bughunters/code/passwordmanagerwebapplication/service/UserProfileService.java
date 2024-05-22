package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserProfileService {

    private final UserProfileRepository profileRepository;

    public UserProfileService(UserProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional // Ensures all changes are committed to the database
    public ProfileResponse updateProfile(ProfilesFromFront profilesFromFront, User user) {
        UserProfiles profiles = user.getUserProfiles();

        // Update profile information
        profiles.setProfileImage(profilesFromFront.getProfileImage());
        profiles.setFirstName(profilesFromFront.getFirstName());
        profiles.setLastName(profilesFromFront.getLastName());

        // Save the updated profile
        UserProfiles updatedProfile = profileRepository.save(profiles);

        // Map the updated profile to a response object
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setEmail(user.getEmail()); // Assuming email is part of the User entity
        profileResponse.setProfileImage(updatedProfile.getProfileImage());
        profileResponse.setFirstName(updatedProfile.getFirstName());
        profileResponse.setLastName(updatedProfile.getLastName());

        return profileResponse;
    }

    public ProfileResponse getUserProfile(User user) {
        UserProfiles userProfile = user.getUserProfiles();
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile not found");
        }

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setEmail(user.getEmail()); // Assuming email is part of the User entity
        profileResponse.setProfileImage(userProfile.getProfileImage());
        profileResponse.setFirstName(userProfile.getFirstName());
        profileResponse.setLastName(userProfile.getLastName());

        return profileResponse;
    }

    @Transactional // Ensures all changes are committed to the database
    public String deleteUserProfile(User user) {
        UserProfiles userProfile = user.getUserProfiles();
        if (userProfile == null) {
            throw new IllegalArgumentException("User profile not found");
        }

        profileRepository.delete(userProfile);
        return "deleted successfully";
    }
}
