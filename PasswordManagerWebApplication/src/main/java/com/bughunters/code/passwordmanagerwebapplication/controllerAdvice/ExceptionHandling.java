package com.bughunters.code.passwordmanagerwebapplication.controllerAdvice;

import com.bughunters.code.passwordmanagerwebapplication.exceptions.*;
import com.bughunters.code.passwordmanagerwebapplication.models.ExceptionModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandling {

    private ResponseEntity<ExceptionModel> createResponseEntity(String message) {
        ExceptionModel exceptionModel = new ExceptionModel();
        exceptionModel.setStatus(HttpStatus.FORBIDDEN);
        exceptionModel.setMessage(message);
        exceptionModel.setDate(new Date());
        return new ResponseEntity<>(exceptionModel, HttpStatus.FORBIDDEN);
    }

   @ExceptionHandler(UserAlreadyExistException.class)
   public ResponseEntity<ExceptionModel> handleUserAlreadyExistException(UserAlreadyExistException exception) {
       log.error("UserAlreadyExistException occurred!");
       return createResponseEntity(exception.getMessage());
   }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ExceptionModel> handleEmailAlreadyExistException(EmailAlreadyExistException exception){
       log.error("EmailAlreadyExistException occurred!");
        return createResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ExceptionModel> handleIncorrectPasswordException(IncorrectPasswordException exception){
      log.error("IncorrectPasswordException occurred!");
       return createResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(TokenRefreshmentException.class)
    public ResponseEntity<ExceptionModel> handleTokenRefreshmentException(TokenRefreshmentException exception){
        log.error("TokenRefreshmentException occurred!");
        return createResponseEntity(exception.getMessage());
   }

    @ExceptionHandler(IncorrectVerificationCodeException.class)
    public ResponseEntity<ExceptionModel> IncorrectVerificationCodeException(IncorrectVerificationCodeException exception){
        log.error("IncorrectVerificationCodeException occurred!");
        return createResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(MailSendingException.class)
    public ResponseEntity<ExceptionModel> MailSendingException(MailSendingException exception){
       log.error("MailSendingException occurred!");
        return createResponseEntity(exception.getMessage());
   }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionModel> UserNotFoundException(UserNotFoundException exception){
       log.error("UserNotFoundException occurred!");
       return createResponseEntity(exception.getMessage());
   }
   @ExceptionHandler(PasswordUpdationException.class)
    public ResponseEntity<ExceptionModel> passwordUpdationException(PasswordUpdationException e){
        log.error("error occurred while updating password");
        return createResponseEntity(e.getMessage());
   }

}




