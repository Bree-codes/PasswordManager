package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.DTO.ChangePasswordRequest;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.IncorrectPasswordException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserNotFoundException;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void changePassword(String username, ChangePasswordRequest changePasswordRequest){

        //we first retrieve the user from the database using findByUsername method
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            User user1 = user.get();

            //we check whether the old password the user entered and the one in the db match
            if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user1.getPassword())) {

                user1.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                userRepository.save(user1);
            } else {
                throw new IncorrectPasswordException("Passwords don't match!");
            }
        }

        else{
            throw new UserNotFoundException("User does not exist!");
            }

    }
}
