package com.bughunters.code.passwordmanagerwebapplication.controllerAdvice;

import com.bughunters.code.passwordmanagerwebapplication.exceptions.UserAlreadyExistException;
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

    private final ExceptionModel exceptionModel;

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionModel> handleUserAlreadyExistException(UserAlreadyExistException exception){
        log.error("Email Already ExistException occurred ");

        exceptionModel.setStatus(HttpStatus.FORBIDDEN);
        exceptionModel.setMessage(exception.getMessage());
        exceptionModel.setDate(new Date());

        return  new ResponseEntity<>(exceptionModel,HttpStatus.FORBIDDEN);
    }




}
