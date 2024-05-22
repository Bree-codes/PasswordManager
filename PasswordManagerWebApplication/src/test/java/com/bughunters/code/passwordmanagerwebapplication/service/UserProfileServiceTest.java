package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserProfileServiceTest {

    @InjectMocks
    private UserProfileService userProfileService;

    @Mock
    private UserProfileRepository profileRepository;

    @Test
    public void testUpdateProfile() {
        // Create a sample user profile
        UserProfiles userProfile = new UserProfiles();
        userProfile.setProfileImage(new byte[0]);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");

        // Create a sample user
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setUserProfiles(userProfile);

        // Create a sample ProfilesFromFront object
        ProfilesFromFront profilesFromFront = new ProfilesFromFront();
        profilesFromFront.setProfileImage(new byte[0]);
        profilesFromFront.setFirstName("Jane");
        profilesFromFront.setLastName("Doe");

        // Mock the profileRepository.save() method
        when(profileRepository.save(any(UserProfiles.class))).thenReturn(userProfile);

        // Call the updateProfile() method
        ProfileResponse profileResponse = userProfileService.updateProfile(profilesFromFront, user);

        // Verify that profileRepository.save() was called with the correct argument
        verify(profileRepository, times(1)).save(any(UserProfiles.class));

        // Assert that the response contains the correct data
        assertEquals(user.getEmail(), profileResponse.getEmail());
        assertEquals(profilesFromFront.getProfileImage(), profileResponse.getProfileImage());
        assertEquals(profilesFromFront.getFirstName(), profileResponse.getFirstName());
        assertEquals(profilesFromFront.getLastName(), profileResponse.getLastName());
    }

    @Test
    public void testGetUserProfile() {
        // Mock data
        User user = new User();
        user.setEmail("john@example.com");

        UserProfiles userProfile = new UserProfiles();
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");

        user.setUserProfiles(userProfile);

        // Test method
        ProfileResponse result = userProfileService.getUserProfile(user);

        // Assertions
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(userProfile.getFirstName(), result.getFirstName());
        assertEquals(userProfile.getLastName(), result.getLastName());
    }

    @Test
    public void testDeleteUserProfile() {
        // Mock data
        User user = new User();
        UserProfiles userProfile = new UserProfiles();
        user.setUserProfiles(userProfile);

        // Test method
        userProfileService.deleteUserProfile(user);

        // Verify interactions
        verify(profileRepository, times(1)).delete(userProfile);
    }
}
