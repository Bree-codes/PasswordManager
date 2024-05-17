package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.configuration.CryptoDetailsUtils;
import com.bughunters.code.passwordmanagerwebapplication.entity.ManagedPassword;
import com.bughunters.code.passwordmanagerwebapplication.models.ManagingPasswords;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
