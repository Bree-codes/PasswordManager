package com.bughunters.code.passwordmanagerwebapplication.models;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import lombok.Data;

@Data
public class ManagingPasswords {
    private long userId;
    private String websiteName;
    private String username;
    private String password;


    public ManagingPasswords(long userId, String decryptedPassword, String username, String websiteName) {

    }
}
