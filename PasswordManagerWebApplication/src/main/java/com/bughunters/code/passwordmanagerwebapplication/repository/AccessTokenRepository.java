package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.AccessTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccessTokenRepository extends CrudRepository<AccessTokenTable, Long> {

    Optional<List<AccessTokenTable>> findAllByUserAndIsLoggedOutEqualsFalse(User user);
}
