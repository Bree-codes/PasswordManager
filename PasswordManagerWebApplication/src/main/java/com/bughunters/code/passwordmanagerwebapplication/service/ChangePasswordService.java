package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.IncorrectPasswordException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void changePassword(Long Id, ChangePasswordRequest changePasswordRequest){

        User user = userRepository.findById(Id).orElseThrow(()-> new UserNotFoundException("User not found!"));


        //we check whether the old password the user entered and the one in the db match
            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {

                user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user);
            } else {
                throw new IncorrectPasswordException("Passwords don't match!");
            }
        }

    }

