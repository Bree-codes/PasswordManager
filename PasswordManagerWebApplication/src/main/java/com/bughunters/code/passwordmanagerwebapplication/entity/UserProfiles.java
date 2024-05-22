package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String profileId;
    private long userId;
    @Lob
    private byte[] profileImage;
    private String firstName;
    private String lastName;
}
