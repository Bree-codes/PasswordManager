package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.ProfileDeletionException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.ProfileNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.ProfileUpdateException;
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
      try {
          UserProfiles profiles = user.getUserProfiles();
          if (profiles == null){
              throw new ProfileNotFoundException("profiles for user not found");
          }

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
      }catch (Exception e){
          throw new ProfileUpdateException("could not update profile, try updating again");
      }
    }

    public ProfileResponse getUserProfile(User user) {
        try {
            UserProfiles userProfile = user.getUserProfiles();
            if (userProfile == null) {
                throw new ProfileNotFoundException("User profile not found");
            }

            log.info("getting the profile for userId :{}",user.getId());
            ProfileResponse profileResponse = new ProfileResponse();
            profileResponse.setEmail(user.getEmail());
            profileResponse.setProfileImage(userProfile.getProfileImage());
            profileResponse.setFirstName(userProfile.getFirstName());
            profileResponse.setLastName(userProfile.getLastName());

            return profileResponse;
        }catch (Exception e){
            throw new ProfileNotFoundException("could not obtain profile for user");
        }
    }

    @Transactional // Ensures all changes are committed to the database
    public String deleteUserProfile(User user) {
        try {
            UserProfiles userProfile = user.getUserProfiles();
            if (userProfile == null) {
                throw new ProfileNotFoundException("User profile not found");
            }

            profileRepository.delete(userProfile);
            return "deleted successfully";
        }catch (Exception e){
            throw new ProfileDeletionException("could not delete profile,please try again");
        }
    }
}
