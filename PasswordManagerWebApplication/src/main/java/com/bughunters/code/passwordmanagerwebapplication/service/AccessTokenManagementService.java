package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.AccessTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccessTokenManagementService {

    private final JwtService jwtService;

    private final AccessTokenRepository accessTokenRepository;

    public String generateAccessToken(User user){

        /*generate the access token.*/
        AccessTokenTable accessTokenTable = new AccessTokenTable();
        accessTokenTable.setToken(jwtService.generateToken(user));
        accessTokenTable.setLoggedOut(false);
        accessTokenTable.setUser(user);

        //set any previous token as loggedOut.
        accessTokenRepository.findAllByUserAndIsLoggedOut(user, false).ifPresent(
                (accessToken -> {accessToken.forEach((token -> {token.setLoggedOut(true);
                }));
                    accessTokenRepository.saveAll(accessToken);})
        );

        //save the new access token.
        accessTokenRepository.save(accessTokenTable);

        return accessTokenTable.getToken();
    }
}
