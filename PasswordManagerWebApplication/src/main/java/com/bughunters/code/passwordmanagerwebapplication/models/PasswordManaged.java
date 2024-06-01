package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class PasswordManaged {
    private long userId;
    private String websiteName;
    private String username;
    private String password;
    private List<Timestamp> updatedTime;

    public PasswordManaged(long userId, String websiteName, String username,
                           String password, List<Timestamp> updatedTime) {
        this.userId = userId;

        this.websiteName = websiteName;

        this.username = username;

        this.password = password;

        this.updatedTime = updatedTime;
    }
}
