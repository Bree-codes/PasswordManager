package com.bughunters.code.passwordmanagerwebapplication.configuration;

import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.repository.UpdatedPasswordsRepositories;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Slf4j
public class ManagedPasswordListener {


    private  UpdatedPasswordsRepositories updatedPasswordsRepositories;



    @PreUpdate
    public void onPreUpdate(ManagedPassword managedPassword) {
        // Logic to log the update
        log.info("recording time for updating");
        UpdatedPasswordsDetails updatedPassword = new UpdatedPasswordsDetails();
        updatedPassword.setUserid(managedPassword.getUserId());
        updatedPassword.setManagedPasswordId(managedPassword.getManagedPasswordId());
        updatedPassword.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        log.info("recorded successfully, updating ....");

        updatedPasswordsRepositories.save(updatedPassword);
    }
}
