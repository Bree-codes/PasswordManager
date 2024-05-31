package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UpdatedPasswordsRepositories extends JpaRepository<UpdatedPasswordsDetails,Long> {
    Collection<Object> findAllByManagedPasswordId(long s);
}
