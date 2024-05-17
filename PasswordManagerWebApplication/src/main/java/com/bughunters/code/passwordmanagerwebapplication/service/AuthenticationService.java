package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.EmailAlreadyExistException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.IncorrectVerificationCodeException;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserAlreadyExistException;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.request.LoginRequest;
import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.AuthorizationResponse;
import com.bughunters.code.passwordmanagerwebapplication.response.EmailVerificationResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailingService mailingService;

    private final AuthenticationManager authenticationManager;

    private final VerificationCodeManagementService verificationCodeManagementService;

    private final RefreshCookieManagementService refreshCookieManagementService;

    public ResponseEntity<AuthorizationResponse> registerUser(
            RegistrationRequest registrationRequest) {

        //check if the username already exist.
        userRepository.findByUsername(registrationRequest.getUsername()).ifPresent(
                (user) -> { throw new UserAlreadyExistException("Username Already Exist");} /*1*/
        );

        /*Check if the email is already in use.*/
        userRepository.findByEmail(registrationRequest.getEmail()).ifPresent(
                (user -> {throw new EmailAlreadyExistException("User Email Already Exist");}) /*2*/
        );

        log.info("request passed the redundancy check");

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        //adding the new user to the database
        userRepository.save(user);

        /*Send the user email.*/
        mailingService.sendMails(user);

        log.info("Email sent.");

        //response to the user
        AuthorizationResponse registrationResponse = new AuthorizationResponse();
        registrationResponse.setId(user.getId());
        registrationResponse.setMessage("Check Your Email For a Verification Code.");
        registrationResponse.setStatus(HttpStatus.CREATED);

        log.info("Registration complete");
        return new ResponseEntity<>(registrationResponse,HttpStatus.OK);
    }

    public ResponseEntity<AuthorizationResponse> loginUser(
            LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        User user =  userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setId(user.getId());
        authorizationResponse.setStatus(HttpStatus.OK);
        authorizationResponse.setMessage("Login successful!");

        log.info("Login completed");
        return new ResponseEntity<>(authorizationResponse,HttpStatus.OK);
    }

    public ResponseEntity<EmailVerificationResponse> verifyUserEmail(
            Integer code, Long userId, HttpServletResponse response) {

        /*Get the user by id.*/
        if(!verificationCodeManagementService.matchesVerificationCode(
                userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found.")), code)){
            log.error("Incorrect verification code entered.");
            throw new IncorrectVerificationCodeException("You Entered An Incorrect Code!");
        }

        /*Code is correct prepare and give user response cookie.*/
        response.addCookie(refreshCookieManagementService.generateRefreshToken(
                userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found."))));

        //user authentication response.
        EmailVerificationResponse verificationResponse = new EmailVerificationResponse();
        verificationResponse.setToken();
        verificationResponse.setMessage("Email verification successful.");



        return null;
    }
}


