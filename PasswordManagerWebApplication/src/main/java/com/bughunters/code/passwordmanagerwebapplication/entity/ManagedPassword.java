package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ManagedPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String managedPasswordId;
    private long userId;
    private String websiteName;
    private String password;
}
