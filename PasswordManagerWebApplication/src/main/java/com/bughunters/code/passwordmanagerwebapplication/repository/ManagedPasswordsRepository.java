package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional

public interface ManagedPasswordsRepository extends JpaRepository<ManagedPassword,Long> {

    Optional<List<ManagedPassword>> findAllByUserId(long userId);

    Optional<ManagedPassword> findByUserIdAndManagedPasswordId(long userId, long passwordId);
}
