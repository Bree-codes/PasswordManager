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

    private long userid;

    private long managedPasswordId;

    private Timestamp updatedTime;

}
