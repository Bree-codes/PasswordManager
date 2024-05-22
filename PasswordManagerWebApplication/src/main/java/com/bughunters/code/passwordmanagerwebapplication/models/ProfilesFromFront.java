package com.bughunters.code.passwordmanagerwebapplication.models;

import lombok.Data;

@Data
public class ProfilesFromFront {
    private byte[] profileImage;
    private String email;
    private String firstName;
    private String lastName;
}
