package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class RefreshTokenTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date expirationDate;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "refresh_token_fk")
    private User user;

}
