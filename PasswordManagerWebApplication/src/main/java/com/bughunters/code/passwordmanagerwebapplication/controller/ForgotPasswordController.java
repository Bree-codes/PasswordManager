package com.bughunters.code.passwordmanagerwebapplication.controller;

import com.bughunters.code.passwordmanagerwebapplication.DTO.MailBody;
import com.bughunters.code.passwordmanagerwebapplication.DTO.ResetPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.ForgotPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.ForgotPasswordRepo;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import com.bughunters.code.passwordmanagerwebapplication.service.MailingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);
    private final UserRepository userRepository;
    private final MailingService mailingService;
    private final ForgotPasswordRepo forgotPasswordRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/verify/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Please enter a valid Email!"));

        Integer otp = generateOTP();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request: " + otp)
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

    @PostMapping("/verifyOTP/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp,
                                            @PathVariable String email){

        //checking if user exists
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Please enter a valid Email!"));

        //checking if OTP entered by the user exists
        ForgotPassword forgotPassword = forgotPasswordRepo.findByOtpAndUser(otp,user)
                .orElseThrow(()-> new RuntimeException("Invalid OTP!"));

        //checking if OTP is expired
        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepo.deleteById(forgotPassword.getForgotPasswordId());
            return new ResponseEntity<>("OTP is expired!", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP Verified!");
    }

    @PostMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword,
                                                @PathVariable String email){
        log.info("request to reset password.");

        //checking if new password and confirm password are the same
        if(!Objects.equals(resetPassword.password(),resetPassword.confirmPassword())){
            return new ResponseEntity<>("Please Re-Enter the password!",HttpStatus.EXPECTATION_FAILED);
        }
        String encodedPassword = passwordEncoder.encode(resetPassword.password());

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found."));

        log.info("updating the password.");
        user.setPassword(encodedPassword);
        //updating in the db
        userRepository.save(user);

        return ResponseEntity.ok("Your Password has been reset successfully!");
    }

    public Integer generateOTP(){
        Random random = new Random();
        return random.nextInt(0,999_999);
    }

}
