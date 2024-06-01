package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagingPasswords {

    private long userId;

    private String password;

    private String username;

    private String websiteName;
}
