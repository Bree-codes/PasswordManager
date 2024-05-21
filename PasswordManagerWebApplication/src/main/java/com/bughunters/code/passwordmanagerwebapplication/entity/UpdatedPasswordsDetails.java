package com.bughunters.code.passwordmanagerwebapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
public class UpdatedPasswordsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long updatedId;
    private String managedPasswordId;
    private Timestamp updatedTime;
    @ManyToOne
    private ManagedPassword managedPassword;
}
