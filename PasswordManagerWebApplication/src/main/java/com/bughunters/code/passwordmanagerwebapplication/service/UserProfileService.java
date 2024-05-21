package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserProfileService {
    private final UserRepository userRepository;
    private final ManagedPasswordsRepository passwordsRepository;
    private final ModelMapper modelMapper;

    public UserProfileService(UserRepository userRepository, ManagedPasswordsRepository passwordsRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordsRepository = passwordsRepository;
        this.modelMapper = modelMapper;
    }


}
