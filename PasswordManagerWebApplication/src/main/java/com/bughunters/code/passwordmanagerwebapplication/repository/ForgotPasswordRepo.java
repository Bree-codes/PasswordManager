package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.ForgotPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword,Integer> {

    @Query("select forgotPassword from ForgotPassword forgotPassword where forgotPassword.otp =?1 and forgotPassword.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}
