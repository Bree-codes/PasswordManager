package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserProfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profileId;
    private byte[] profileImage;
    private String firstName;
    private String lastName;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
