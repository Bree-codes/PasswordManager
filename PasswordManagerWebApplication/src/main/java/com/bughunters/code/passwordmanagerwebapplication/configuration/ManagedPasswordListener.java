package com.bughunters.code.passwordmanagerwebapplication.configuration;

import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.repository.UpdatedPasswordsRepositories;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ManagedPasswordListener {

    @Autowired
    private UpdatedPasswordsRepositories updatedPasswordsRepositories;

    @PreUpdate
    public void onPreUpdate(ManagedPassword managedPassword) {
        // Logic to log the update
        UpdatedPasswordsDetails updatedPassword = new UpdatedPasswordsDetails();
        updatedPassword.setUserid(managedPassword.getUserId());
        updatedPassword.setManagedPasswordId(managedPassword.getManagedPasswordId());
        updatedPassword.setUpdatedTime(new Timestamp(System.currentTimeMillis()));

        updatedPasswordsRepositories.save(updatedPassword);
    }
}
