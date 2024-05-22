package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.*;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import com.bughunters.code.passwordmanagerwebapplication.repository.UpdatedPasswordsRepositories;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManagingPasswordsService {

    private final CryptoDetailsUtils cryptoDetailsUtils;
    private final ModelMapper modelMapper;
    private final ManagedPasswordsRepository passwordsRepository;
    private final UpdatedPasswordsRepositories updatedPasswordsRepository;

    public ManagingPasswordsService(CryptoDetailsUtils cryptoDetailsUtils,
                                    ModelMapper modelMapper,
                                    ManagedPasswordsRepository passwordsRepository,
                                    UpdatedPasswordsRepositories updatedPasswordsRepository) {
        this.cryptoDetailsUtils = cryptoDetailsUtils;
        this.modelMapper = modelMapper;
        this.passwordsRepository = passwordsRepository;
        this.updatedPasswordsRepository = updatedPasswordsRepository;

        // Initialize model mapper configuration once
        this.modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public List<MappedDetailsResponse> managePasswords(List<ManagingPasswords> passwords) {
        log.info("Service to manage passwords");

        try {
            List<ManagedPassword> managedPasswordList;
            managedPasswordList = passwords.stream()
                    .map(this::convertToManagedPassword)
                    .collect(Collectors.toList());

            passwordsRepository.saveAll(managedPasswordList);

            return managedPasswordList.stream()
                    .map(p -> modelMapper.map(p, MappedDetailsResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error managing passwords", e);
            throw new ManagingPasswordsException("could not manage password");
        }
    }

    private ManagedPassword convertToManagedPassword(ManagingPasswords managingPasswords) {
        try {
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
            ManagedPassword managedPassword = new ManagedPassword();
            managedPassword.setUserId(managingPasswords.getUserId());
            managedPassword.setWebsiteName(managingPasswords.getWebsiteName());
            managedPassword.setUsername(managingPasswords.getUsername());
            managedPassword.setPassword(cryptoDetailsUtils.encrypt(managingPasswords.getPassword()));
            return managedPassword;
        } catch (Exception e) {
            log.error("Error encrypting password for userId: {}", managingPasswords.getUserId(), e);
            throw new PasswordEncryptionException("Encryption failed");
        }
    }

    public List<ManagingPasswords> decrypt(long userId) throws Exception {
        log.info("Service to decrypt details for userId: {}", userId);

        try {
            Optional<List<ManagedPassword>> optionalPasswords = passwordsRepository.findAllByUserId(userId);
            if (optionalPasswords.isEmpty()) {
                log.warn("No passwords found for userId: {}", userId);
                throw new ChangeSetPersister.NotFoundException();
            }

            List<ManagedPassword> managedPasswords = optionalPasswords.get();
            return managedPasswords.stream()
                    .map(this::decryptPassword)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while decrypting passwords for userId: {}", userId);
            throw new PasswordDecryptionException("could not decrypt password");
        }
    }

    private ManagingPasswords decryptPassword(ManagedPassword managedPassword) {
        try {
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
            String decryptedPassword = cryptoDetailsUtils.decrypt(managedPassword.getPassword());
            return new ManagingPasswords(managedPassword.getUserId(), decryptedPassword, managedPassword.getUsername(), managedPassword.getWebsiteName());
        } catch (Exception e) {
            log.error("Error decrypting password for managedPasswordId: {}", managedPassword.getUserId(), e);
            throw new PasswordDecryptionException("Decryption failed");
        }
    }

    public ManagingPasswords updateDetails(long userId, ManagingPasswords passwords) throws Exception {
        log.info("Updating managed details for userId: {}", userId);

        try {
            Optional<ManagedPassword> toUpdate = passwordsRepository.findByUserId(userId);
            if (toUpdate.isEmpty()) {
                log.warn("Password with userId {} not found", userId);
                throw new PasswordNotFoundException("Password with userId " + userId + " not found");
            }

            ManagedPassword updatePassword = toUpdate.get();
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
            updatePassword.setWebsiteName(passwords.getWebsiteName());
            updatePassword.setPassword(cryptoDetailsUtils.encrypt(passwords.getPassword()));

            passwordsRepository.save(updatePassword);

            // Save update details in UpdatedPassword table
            UpdatedPasswordsDetails updatedPassword = new UpdatedPasswordsDetails();
            updatedPassword.setUserid(userId);
            updatedPassword.setManagedPasswordId(updatePassword.getManagedPasswordId());
            updatedPassword.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));
            updatedPasswordsRepository.save(updatedPassword);

            log.info("Update success for userId: {}", userId);

            return modelMapper.map(updatePassword, ManagingPasswords.class);
        } catch (Exception e) {
            log.error("Error updating details for userId: {}", userId, e);
            throw new PasswordUpdationException("could not update the password details");
        }
    }

    public ResponseEntity<String> deletePasswordByUserIdAndManaged(long userId, String passwordId) {
        log.info("Deleting password with ID {} for userId: {}", passwordId, userId);

        try {
            Optional<ManagedPassword> toDelete = passwordsRepository.findByUserIdAndManagedPasswordId(userId, passwordId);
            if (toDelete.isEmpty()) {
                log.warn("Password with ID {} not found for userId: {}", passwordId, userId);
                throw new PasswordNotFoundException("Password with ID " + passwordId + " not found for userId " + userId);
            }

            passwordsRepository.delete(toDelete.get());
            log.info("Password with ID {} deleted successfully for userId: {}", passwordId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting password with ID {} for userId: {}", passwordId, userId, e);
            throw new PasswordDeletionException("could not delete the password details, try again");
        }
    }
}
