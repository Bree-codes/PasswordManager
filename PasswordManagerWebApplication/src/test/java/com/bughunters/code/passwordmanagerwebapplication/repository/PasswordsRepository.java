package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordsRepository extends JpaRepository<ManagedPassword,String> {
}
