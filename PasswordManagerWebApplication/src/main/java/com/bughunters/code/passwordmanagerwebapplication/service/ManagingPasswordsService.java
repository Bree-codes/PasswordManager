package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.DTO.UpdatedResponse;
import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.entity.UpdatedPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.*;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.models.MappedDetailsResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.PasswordManaged;
import com.bughunters.code.passwordmanagerwebapplication.models.UpdatingPasswordsDetails;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import com.bughunters.code.passwordmanagerwebapplication.repository.UpdatedPasswordsRepositories;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            throw new ManagingPasswordsException("Could not manage password");
        }
    }

    private ManagedPassword convertToManagedPassword(ManagingPasswords managingPasswords) {
        try {
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=");
            ManagedPassword managedPassword = new ManagedPassword();
            managedPassword.setUserId(managingPasswords.getUserId());
            managedPassword.setWebsiteName(managingPasswords.getWebsiteName());
            managedPassword.setUsername(managingPasswords.getUsername());
            managedPassword.setPassword(cryptoDetailsUtils.encrypt(managingPasswords.getPassword()));
            managedPassword.setCreatedTime(new Timestamp(System.currentTimeMillis()));


            return managedPassword;
        } catch (Exception e) {
            log.error("Error encrypting password for userId: {}", managingPasswords.getUserId(), e);
            throw new PasswordEncryptionException("Encryption failed");
        }
    }

    public List<PasswordManaged> decrypt(long userId) throws Exception {
        log.info("Service to decrypt details for userId: {}", userId);

        try {
            List<ManagedPassword> optionalPasswords = passwordsRepository.findAllByUserId(userId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            return optionalPasswords.stream()
                    .map(this::decryptPassword)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while decrypting passwords for userId: {}", userId);
            throw new PasswordDecryptionException("Could not decrypt password");
        }
    }


    private PasswordManaged decryptPassword(ManagedPassword managedPassword) {
        try {
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=");
            String decryptedPassword = cryptoDetailsUtils.decrypt(managedPassword.getPassword());
            ManagedPassword createdTime = passwordsRepository.findByUserIdAndManagedPasswordId(managedPassword.getUserId(), managedPassword.getManagedPasswordId())
                    .orElseThrow();

            UpdatedPasswordsDetails updatedPasswordsDetails = new UpdatedPasswordsDetails();
            // Fetch update times
            List<Timestamp> updateTimes = new ArrayList<>();

            updateTimes.add(createdTime.getCreatedTime());

                    updateTimes.addAll(updatedPasswordsRepository.findAllByManagedPasswordId(managedPassword.getManagedPasswordId())
                    .stream()
                    .map(UpdatedPasswordsDetails::getUpdatedTime)
                    .toList());

            return new PasswordManaged(managedPassword.getUserId(), decryptedPassword, managedPassword.getUsername(), managedPassword.getWebsiteName(),updateTimes);
        } catch (Exception e) {
            log.error("Error decrypting password for managedPasswordId: {}", managedPassword.getUserId(), e);
            throw new PasswordDecryptionException("Decryption failed");
        }
    }


    public UpdatedResponse updateDetails(long userId, UpdatingPasswordsDetails passwordsDetails, long managedPasswordId) throws Exception {
        log.info("Updating managed details for userId: {}", userId);

        try {
            // Fetch the existing managed password entry
            ManagedPassword updatePassword = passwordsRepository.findByUserIdAndManagedPasswordId(userId, managedPasswordId)
                    .orElseThrow(() -> new PasswordUpdationException("Managed password not found"));


            // Initialize the CryptoDetailsUtils with the secret key
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=");

            log.info("Fetched existing managed password details successfully:{}",updatePassword);

            // Update the managed password details
            updatePassword.setWebsiteName(passwordsDetails.getWebsiteName());
            log.info("Website name updated");

            updatePassword.setUsername(passwordsDetails.getUsername());
            log.info("Username updated");
            // Encrypt the password before saving
            updatePassword.setPassword(cryptoDetailsUtils.encrypt(passwordsDetails.getPassword()));
            log.info("Password encrypted and updated");
            log.info("updated details: {}", updatePassword);
            // Save the updated managed password entry
            passwordsRepository.updatePassword(passwordsDetails.getUsername(), passwordsDetails.getWebsiteName(),
                    updatePassword.getPassword(), userId, managedPasswordId)
            ;
            log.info("recording time for updating");
            UpdatedPasswordsDetails updatedPassword = new UpdatedPasswordsDetails();
            updatedPassword.setUserid(updatePassword.getUserId());
            updatedPassword.setManagedPasswordId(updatePassword.getManagedPasswordId());
            updatedPassword.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
            log.info("recorded successfully, updating {}....",updatedPassword);

            updatedPasswordsRepository.save(updatedPassword);

            UpdatedResponse updatedResponse = new UpdatedResponse();
            updatedResponse.setMessage("update successful");

            return updatedResponse;

        } catch (Exception e) {
            log.error("Error updating details for userId: {}", userId, e);
            throw new PasswordUpdationException("Could not update the password details");
        }
    }



    public ResponseEntity<String> deletePasswordByUserIdAndManaged(long userId, long passwordId) {
        log.info("Deleting password with ID {} for userId: {}", passwordId, userId);

        try {
            Optional<ManagedPassword> toDelete = passwordsRepository.findByUserIdAndManagedPasswordId(userId, passwordId);
            if (toDelete.isEmpty()) {
                log.warn("Password with ID {} not found for userId: {}", passwordId, userId);
                throw new PasswordNotFoundException("Password with ID " + passwordId + " not found for userId " + userId);
            }

            passwordsRepository.delete(toDelete.get());
            log.info("Password with ID {} deleted successfully for userId: {}", passwordId, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting password with ID {} for userId: {}", passwordId, userId, e);
            throw new PasswordDeletionException("Could not delete the password details, try again");
        }
    }
}
