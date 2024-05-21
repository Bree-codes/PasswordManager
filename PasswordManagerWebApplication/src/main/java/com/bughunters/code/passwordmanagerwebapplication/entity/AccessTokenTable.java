package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class AccessTokenTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean isLoggedOut;

    @ManyToOne()
    @JoinColumn(name = "access_token_fk")
    private User user;
}
