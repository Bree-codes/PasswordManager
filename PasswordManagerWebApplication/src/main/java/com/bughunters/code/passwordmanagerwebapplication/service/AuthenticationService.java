package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.EmailAlreadyExistException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserAlreadyExistException;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.RegistrationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<RegistrationResponse> registerUser(
            RegistrationRequest registrationRequest, HttpServletResponse response) {

        //check if the username already exist.
        userRepository.findByUsername(registrationRequest.getUsername()).ifPresent(
                (user) -> { throw new UserAlreadyExistException("Username Already Exist");}
        );

        /*Check if the email is already in use.*/
        userRepository.findByEmail(registrationRequest.getEmail()).ifPresent(
                (user -> {throw new EmailAlreadyExistException("User Email Already Exist");})
        );

        log.info("request passed the redundancy check");

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        //adding the new user to the database
        userRepository.save(user);

        //response to the user
        RegistrationResponse registrationResponse = new RegistrationResponse();

        registrationResponse.setId(user.getId());
        registrationResponse.setMessage("Registration Successful");
        registrationResponse.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(registrationResponse,HttpStatus.OK);

    }

}


