package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

@Data
public class ManagingPasswords {
    private long userId;
    private String websiteName;
    private String password;
}
