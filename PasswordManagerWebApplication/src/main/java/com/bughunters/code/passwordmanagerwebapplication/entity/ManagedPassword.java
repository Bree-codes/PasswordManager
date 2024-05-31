package com.bughunters.code.passwordmanagerwebapplication.entity;

import com.bughunters.code.passwordmanagerwebapplication.configuration.ManagedPasswordListener;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.event.EventListener;

import java.sql.Timestamp;

@Data
@Entity
@EntityListeners(ManagedPasswordListener.class)
public class ManagedPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long managedPasswordId;

    private long userId;

    private String websiteName;

    private String username;

    private String password;

    private Timestamp createdTime;
}
