package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.VerificationCodes;
import com.bughunters.code.passwordmanagerwebapplication.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeManagementService {

    private final VerificationCodeRepository verificationCodeRepository;

    public Integer generateVerificationCode(User user) {

        /*verification code object.*/
        VerificationCodes code = new VerificationCodes();
        code.setUser(user);
        code.setCode(Integer.parseInt(new DecimalFormat("000000").format(new Random().nextInt(999999))));

        /*delete any previous codes*/
        verificationCodeRepository.deleteByUser(user);

        /*save the verification code to the db*/
        verificationCodeRepository.save(code);

        return code.getCode();
    }

    public boolean matchesVerificationCode(User user, Integer verificationCode){

       if(verificationCodeRepository.findVerificationCodesByUser(user).orElseThrow().equals(verificationCode)){
           /*Deleting the user code.*/
           verificationCodeRepository.deleteByUser(user);
           return true;
       }
        return false;
    }
}
