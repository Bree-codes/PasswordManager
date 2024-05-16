package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VerificationCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    @OneToOne
    @JoinColumn(name = "code_fk")
    private User user;
}
