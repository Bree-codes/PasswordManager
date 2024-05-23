package com.bughunters.code.passwordmanagerwebapplication.models;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagingPasswords {
    private long userId;

    private String websiteName;

    private String username;

    private String password;

    private List<String> updateDate;

    public ManagingPasswords(long userId, String decryptedPassword, String username, String websiteName) {
    }
}
