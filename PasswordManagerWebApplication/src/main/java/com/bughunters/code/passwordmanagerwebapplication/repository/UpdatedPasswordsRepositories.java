package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatedPasswordsRepositories extends JpaRepository<UpdatedPasswordsDetails,Long> {
}
