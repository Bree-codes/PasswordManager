package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.DTO.MailBody;
import com.bughunters.code.passwordmanagerwebapplication.entity.ForgotPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.ForgotPasswordRepo;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final MailingService mailingService;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/verify/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Please enter a valid Email!"));

        Integer otp = generateOTP();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request" + otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 60 * 1000))
                .user(user)
                .build();

        mailingService.sendSimpleMessage(mailBody);
        forgotPasswordRepo.save(forgotPassword);

        return ResponseEntity.ok("Email sent for Verification!");
    }

    @PostMapping("/verifyOTP/{otp}/{Email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp,@PathVariable String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Please enter a valid Email!"));

    }



    public Integer generateOTP(){
        Random random = new Random();
        return random.nextInt(0,999_999);
    }

}
