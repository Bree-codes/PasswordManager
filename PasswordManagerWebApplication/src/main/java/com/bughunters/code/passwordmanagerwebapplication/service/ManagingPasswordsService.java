package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ManagingPasswordsService {

    private final CryptoDetailsUtils cryptoDetailsUtils;
    private final ModelMapper modelMapper;
    private final ManagedPasswordsRepository passwordsRepository;

    public ManagingPasswordsService(CryptoDetailsUtils cryptoDetailsUtils,
                                    ModelMapper modelMapper,
                                    ManagedPasswordsRepository passwordsRepository) {
        this.cryptoDetailsUtils = cryptoDetailsUtils;
        this.modelMapper = modelMapper;
        this.passwordsRepository = passwordsRepository;

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
            throw new RuntimeException(e.getMessage(), e);
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
            throw new RuntimeException("Encryption failed", e);
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
            log.error("Error while decrypting passwords for userId: {}", userId, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ManagingPasswords decryptPassword(ManagedPassword managedPassword) {
        try {
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
            String decryptedPassword = cryptoDetailsUtils.decrypt(managedPassword.getPassword());
            return new ManagingPasswords(managedPassword.getUserId(), decryptedPassword, managedPassword.getUsername(), managedPassword.getWebsiteName());
        } catch (Exception e) {
            log.error("Error decrypting password for managedPasswordId: {}", managedPassword.getUserId(), e);
            throw new RuntimeException("Decryption failed", e);
        }
    }

    public ManagingPasswords updateDetails(long userId, ManagingPasswords passwords) throws Exception {
        log.info("Updating managed details for userId: {}", userId);

        try {
            Optional<ManagedPassword> toUpdate = passwordsRepository.findByUserId(userId);
            if (toUpdate.isEmpty()) {
                log.warn("Password with userId {} not found", userId);
                throw new IllegalArgumentException("Password with userId " + userId + " not found");
            }

            ManagedPassword updatePassword = toUpdate.get();
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
            updatePassword.setWebsiteName(passwords.getWebsiteName());
            updatePassword.setPassword(cryptoDetailsUtils.encrypt(passwords.getPassword()));
            updatePassword.setUserId(userId);

            passwordsRepository.save(updatePassword);
            log.info("Update success for userId: {}", userId);

            return modelMapper.map(updatePassword, ManagingPasswords.class);
        } catch (Exception e) {
            log.error("Error updating details for userId: {}", userId, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    public ResponseEntity<String> deletePasswordByUserIdAndPasswordId(long userId, long passwordId) {
        log.info("Deleting password with ID {} for userId: {}", passwordId, userId);

        try {
            Optional<ManagedPassword> toDelete = passwordsRepository.findByUserIdAndPasswordId(userId, passwordId);
            if (toDelete.isEmpty()) {
                log.warn("Password with ID {} not found for userId: {}", passwordId, userId);
                throw new IllegalArgumentException("Password with ID " + passwordId + " not found for userId " + userId);
            }

            passwordsRepository.delete(toDelete.get());
            log.info("Password with ID {} deleted successfully for userId: {}", passwordId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting password with ID {} for userId: {}", passwordId, userId, e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }




}
