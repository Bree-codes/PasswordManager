package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.AccessTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenManagementService {

    private final JwtService jwtService;

    private final AccessTokenRepository accessTokenRepository;

    public String generateAccessToken(User user){

        /*generate the access token.*/
        AccessTokenTable accessTokenTable = new AccessTokenTable();
        accessTokenTable.setToken(jwtService.generateToken(user));
        accessTokenTable.setIsLoggedOut(false);
        accessTokenTable.setUser(user);

        //set any previous token as loggedOut.
        accessTokenRepository.findAllByUserAndIsLoggedOut(user, false).ifPresent(
                (accessToken -> {
                    accessToken.setIsLoggedOut(true);

                    accessTokenRepository.save(accessToken);
                })
        );


        //save the new access token.
        //accessTokenRepository.save(accessTokenTable);

        return null;
    }
}
