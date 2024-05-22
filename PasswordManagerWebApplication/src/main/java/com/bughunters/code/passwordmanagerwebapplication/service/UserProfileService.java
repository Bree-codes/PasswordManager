package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.UserProfiles;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfileResponse;
import com.bughunters.code.passwordmanagerwebapplication.models.ProfilesFromFront;
import com.bughunters.code.passwordmanagerwebapplication.repository.ManagedPasswordsRepository;
import com.bughunters.code.passwordmanagerwebapplication.repository.UserProfileRepository;
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
    private final UserProfileRepository profileRepository;


    public UserProfileService(UserRepository userRepository, ManagedPasswordsRepository passwordsRepository, ModelMapper modelMapper, UserProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.passwordsRepository = passwordsRepository;
        this.modelMapper = modelMapper;
        this.profileRepository = profileRepository;
    }

    public ProfileResponse updateProfile(long userId,ProfilesFromFront profiles){

        UserProfiles userProfiles = profileRepository.findByUserId(userId);
        return null;
    }
}
