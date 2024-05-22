package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfiles,Long> {
    UserProfiles findByUserId(long userId);
}
