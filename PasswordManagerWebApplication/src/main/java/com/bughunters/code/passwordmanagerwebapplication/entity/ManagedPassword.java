package com.bughunters.code.passwordmanagerwebapplication.entity;

import com.bughunters.code.passwordmanagerwebapplication.configuration.ManagedPasswordListener;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.event.EventListener;

@Data
@Entity
@EntityListeners(ManagedPasswordListener.class)
public class ManagedPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String managedPasswordId;
    private long userId;
    private String websiteName;
    private String username;
    private String password;
}
