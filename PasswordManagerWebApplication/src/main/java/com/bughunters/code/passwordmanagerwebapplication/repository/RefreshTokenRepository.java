package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.RefreshTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenTable, Long> {
    Optional<RefreshTokenTable> findByRefreshToken(String refreshToken);

    void deleteAllByUser(User user);

}
