package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.IncorrectPasswordException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> changePassword(Long id, ChangePasswordRequest changePasswordRequest) {

        // Fetch the user by ID, or throw an exception if not found
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));

        // Check if the current password matches the user's password
        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {

            // Check if the new password and the confirmation password match
            if (!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmNewPassword())) {
                return new ResponseEntity<>("Passwords don't match! Enter the password again.", HttpStatus.CONFLICT);
            }

            // Update the user's password with the new encoded password
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);

            return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
        }
        else {
            // Throw an exception if the current password does not match with the password in the database
            throw new IncorrectPasswordException("Current password is incorrect!");
        }
    }

}

