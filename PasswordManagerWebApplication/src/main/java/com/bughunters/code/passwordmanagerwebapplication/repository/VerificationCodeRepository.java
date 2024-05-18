package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.entity.VerificationCodes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface VerificationCodeRepository extends CrudRepository<VerificationCodes, Long> {

    void deleteByUser(User user);

    @Query("select T.code from VerificationCodes as T where T.user =:user")
    Optional<Integer> findVerificationCodesByUser(User user);

}
