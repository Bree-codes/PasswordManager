package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

@Data
public class UpdatingPasswordsDetails {
    private String password;
    private String username;
    private String websiteName;
    public UpdatingPasswordsDetails(String password, String username, String websiteName) {
        this.password = password;
        this.username = username;
        this.websiteName = websiteName;
    }
}
