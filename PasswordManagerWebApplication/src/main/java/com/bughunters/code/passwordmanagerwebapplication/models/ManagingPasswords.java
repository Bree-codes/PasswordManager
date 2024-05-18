package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

@Data
public class ManagingPasswords {
    private long userId;
    private String websiteName;
    private String password;

    public ManagingPasswords(int i, String user1, String password1, String website1) {

    }
}
