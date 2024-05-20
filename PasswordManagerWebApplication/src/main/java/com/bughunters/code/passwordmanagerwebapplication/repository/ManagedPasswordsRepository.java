package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagedPasswordsRepository extends JpaRepository<ManagedPassword,String> {



    Optional<List<ManagedPassword>> findAllByUserId(long userId);

    Optional<ManagedPassword> findByUserId(long userId);
}
