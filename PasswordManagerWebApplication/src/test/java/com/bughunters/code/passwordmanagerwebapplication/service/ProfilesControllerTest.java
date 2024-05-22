package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.controller.ProfilesController;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfilesControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProfilesController profilesController;

    @Test
    void updateProfile_ReturnsOkResponse() {
        // Arrange
        ProfilesFromFront profilesFromFront = new ProfilesFromFront();
        User user = new User();
        ProfileResponse profileResponse = new ProfileResponse();
        when(userProfileService.updateProfile(profilesFromFront, user)).thenReturn(profileResponse);

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profilesController.updateProfile(profilesFromFront, user);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(profileResponse, responseEntity.getBody());
        verify(userProfileService, times(1)).updateProfile(profilesFromFront, user);
    }

    @Test
    void getUserProfile_WhenUserExists_ReturnsOkResponse() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        ProfileResponse profileResponse = new ProfileResponse();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userProfileService.getUserProfile(user)).thenReturn(profileResponse);

        // Act
        ResponseEntity<?> responseEntity = profilesController.getUserProfile(userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(profileResponse, responseEntity.getBody());
        verify(userRepository, times(1)).findById(userId);
        verify(userProfileService, times(1)).getUserProfile(user);
    }

    @Test
    void getUserProfile_WhenUserDoesNotExist_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> profilesController.getUserProfile(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userProfileService, never()).getUserProfile(any());
    }

    @Test
    void deleteProfile_WhenUserExists_ReturnsOkResponse() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<String> responseEntity = profilesController.deleteProfile(userId);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Profile deleted successfully", responseEntity.getBody());
        verify(userRepository, times(1)).findById(userId);
        verify(userProfileService, times(1)).deleteUserProfile(user);
    }

    @Test
    void deleteProfile_WhenUserDoesNotExist_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> profilesController.deleteProfile(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userProfileService, never()).deleteUserProfile(any());
    }
}
