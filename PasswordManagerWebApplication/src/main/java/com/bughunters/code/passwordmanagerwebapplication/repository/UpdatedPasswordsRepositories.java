package com.bughunters.code.passwordmanagerwebapplication.repository;

import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface UpdatedPasswordsRepositories extends JpaRepository<UpdatedPasswordsDetails,Long> {

    Collection<UpdatedPasswordsDetails> findAllByManagedPasswordId(long s);
}
