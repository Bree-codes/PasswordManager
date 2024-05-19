package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
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

    public ManagingPasswordsService(CryptoDetailsUtils cryptoDetailsUtils, ModelMapper modelMapper, ManagedPasswordsRepository passwordsRepository) {
        this.cryptoDetailsUtils = cryptoDetailsUtils;
        this.modelMapper = modelMapper;
        this.passwordsRepository = passwordsRepository;
    }

    public List<ManagingPasswords> managingPasswords(List<ManagingPasswords> passwords)
    {
        log.info("service to manage password");
        try {

            List<ManagedPassword> managedPasswordList = passwords.stream()
                    .map(managingPasswords -> {
                        cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");
                        ManagedPassword managedPassword = new ManagedPassword();

                        managedPassword.setUserId(managingPasswords.getUserId());
                        managedPassword.setWebsiteName(managingPasswords.getWebsiteName());
                        try {
                            managedPassword.setPassword(cryptoDetailsUtils.encrypt(managingPasswords.getPassword()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        return managedPassword;
                    }).toList();

            passwordsRepository.saveAll(managedPasswordList);

            modelMapper.getConfiguration()
                    .setFieldMatchingEnabled(true)
                    .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

            return managedPasswordList.stream()
                    .map(p -> modelMapper.map(p, ManagingPasswords.class))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public ManagingPasswords decrypt(long userId) throws Exception {
        log.info("Service to decrypt details");

        try {
            Optional<ManagedPassword> optionalPassword = passwordsRepository.findByUserId(userId);
            if (optionalPassword.isEmpty()){
                throw new ChangeSetPersister.NotFoundException();
            }
            ManagedPassword password = optionalPassword.get();
            log.info("details fetched successfully");


            // Initialize the encryption/decryption utility with the appropriate key and IV
            cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=", "cfXyXPfwgggkgp0c");

            // Decrypt the encrypted password from the Password object

            String decryptedWebsite = cryptoDetailsUtils.decrypt(password.getWebsiteName());
            log.info("fetching was successful");
            // Return the decrypted password
            return new ManagingPasswords(password.getUserId(),
                    password.getWebsiteName(),decryptedWebsite);
        }catch (Exception e){
            throw new RuntimeException("Password with ID " + userId + " not found");
        }


    }

    public ManagingPasswords updateDetails(long userId, ManagingPasswords passwords) throws Exception{
        log.info("updating managed details");
        Optional<ManagedPassword> toUpdate = passwordsRepository.findByUserId(userId);
        if (toUpdate.isEmpty()){
            throw new IllegalArgumentException("Password with ID " + userId + " not found");
        }
        ManagedPassword updatePassword = toUpdate.get();
        cryptoDetailsUtils.initFromStrings("3k8C9JS6p0d4LwgF+PSa9a4qjNWPh/klCJC3Lm0wmuY=","cfXyXPfwgggkgp0c");
        updatePassword.setWebsiteName(passwords.getWebsiteName());
        updatePassword.setPassword(passwords.getPassword());
        updatePassword.setUserId(userId);
        log.info("update success");

        passwordsRepository.save(updatePassword);


    }

    public HttpStatus deleteDetails(Long id)throws Exception{
        log.info("deleting the details managed for user with id {} ", id);
        Optional<Password> passwordToDelete = passwordsRepository.findById(id);
        if (passwordToDelete.isEmpty()){
            return HttpStatus.NOT_FOUND;
            //return new NotFoundException("the password with id"+id+" does not exist");
        }
        Password delete = passwordToDelete.get();
        passwordsRepository.delete(delete);
        log.info("deletion success for user with id {} ", id);

        return HttpStatus.OK;
    }
}
