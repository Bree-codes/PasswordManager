package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

@Data
public class MappedDetailsResponse {
    private String managedPasswordId;
    private long userId;
    private String websiteName;
    private String username;
    private String password;

}
