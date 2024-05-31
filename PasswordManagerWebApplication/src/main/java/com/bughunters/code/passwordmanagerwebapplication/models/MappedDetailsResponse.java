package com.bughunters.code.passwordmanagerwebapplication.models;

import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MappedDetailsResponse {
    private String managedPasswordId;
    private long userId;
    private String websiteName;
    private String username;
    private String password;
    private Timestamp createdTime;

}
