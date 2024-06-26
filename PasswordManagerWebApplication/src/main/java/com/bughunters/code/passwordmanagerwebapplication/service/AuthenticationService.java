package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.*;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.request.LoginRequest;
import com.bughunters.code.passwordmanagerwebapplication.request.RegistrationRequest;
import com.bughunters.code.passwordmanagerwebapplication.response.AuthorizationResponse;
import com.bughunters.code.passwordmanagerwebapplication.response.EmailVerificationResponse;
import com.bughunters.code.passwordmanagerwebapplication.response.RefreshTokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final AccessTokenManagementService accessTokenManagementService;

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
            LoginRequest loginRequest, HttpServletResponse response){


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));
        }catch (BadCredentialsException e){
            log.warn("login failed!");
            throw new InvalidUsernameOrPasswordException("Invalid username or password!");
        }catch (Exception e){
            System.out.println(e.getClass());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user =  userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User Not Found!"));


        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        authorizationResponse.setId(user.getId());
        authorizationResponse.setStatus(HttpStatus.OK);
        authorizationResponse.setMessage("Login successful!");
        authorizationResponse.setToken(accessTokenManagementService.generateAccessToken(user));

        //generating a new refresh token for the user.
        response.addCookie(refreshCookieManagementService.generateRefreshToken(user)); // passing the user


        log.info("Login successful!");
        return new ResponseEntity<>(authorizationResponse,HttpStatus.OK);
    }

    public ResponseEntity<EmailVerificationResponse> verifyUserEmail(
            Integer code, Long userId, HttpServletResponse response) {

        /*Get the user by id.*/
        if(!verificationCodeManagementService.matchesVerificationCode(
                userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found.")), code)){
            log.error("Incorrect verification code entered."); //getting and passing the user
            throw new IncorrectVerificationCodeException("You Entered An Incorrect Code!");
        }

        /*Code is correct prepare and give user response cookie.*/
        response.addCookie(refreshCookieManagementService.generateRefreshToken(
                userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found.")))); //getting and passing the user

        //user authentication response.
        EmailVerificationResponse verificationResponse = new EmailVerificationResponse();
        verificationResponse.setToken(accessTokenManagementService.generateAccessToken(
                userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found."))));
        verificationResponse.setMessage("Email verification successful.");


        return new ResponseEntity<>(verificationResponse,HttpStatus.OK);
    }

    public ResponseEntity<RefreshTokenResponse> refreshToken(
            HttpServletRequest request, HttpServletResponse response) {
        User user = null;

        log.info("Validating the refresh token.");
        /*get user refreshToken cookie*/
        String userRefreshCookie = request.getHeader("cookie");

        /*Request validation.*/
        if(userRefreshCookie == null){
            log.error("Refresh Token Empty");
            throw new TokenRefreshmentException("Refresh Token Not Found or Corrupted!");
        }

        String[] tokens = userRefreshCookie.split(";");

        for(String token : tokens){
            if(token.startsWith("_token=")){
                /*The cookie is present, lets validate it is still or is viable for token refreshing.*/
                user = refreshCookieManagementService.validateRefreshToken(
                        token.substring(7), response);
                break;
            }
        }

        if(user == null){
            throw new TokenRefreshmentException("Refresh Token NOt Found.");
        }

        //User refresh token response.
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        //generate new access token for the user.
        refreshTokenResponse.setToken(accessTokenManagementService.generateAccessToken(user));
        refreshTokenResponse.setUsername(user.getUsername());
        refreshTokenResponse.setUserId(user.getId());

        log.info("Token refreshed.");
        return new ResponseEntity<>(refreshTokenResponse,HttpStatus.OK);
    }
}


