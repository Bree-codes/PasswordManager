package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.RefreshTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.exceptions.TokenRefreshmentException;
import com.bughunters.code.passwordmanagerwebapplication.repository.RefreshTokenRepository;
import com.bughunters.code.passwordmanagerwebapplication.response.RefreshTokenResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshCookieManagementService {

    private final RefreshTokenRepository refreshTokenRepository;

    public Cookie generateRefreshToken(User user){

        /*Generate and configure the refresh token*/
        RefreshTokenTable refreshTokenTable = new RefreshTokenTable();
        refreshTokenTable.setRefreshToken(UUID.randomUUID().toString());
        refreshTokenTable.setUser(user);
        refreshTokenTable.setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14));

        /*generate and configure user cookie.*/
        Cookie cookie = new Cookie("_token", refreshTokenTable.getRefreshToken());

        cookie.setMaxAge(60 * 60 * 24 * 14);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        //saving the refreshToken details.
        refreshTokenRepository.save(refreshTokenTable);

        return cookie;
    }

    public RefreshTokenResponse validateRefreshToken(String userRefreshCookie, HttpServletResponse response) {

        /*Get the refreshToken details is exist.*/
        RefreshTokenTable refreshTokenTable = refreshTokenRepository.findByRefreshToken(userRefreshCookie)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Invalid Refresh Token!"));

        //check whether the token is expired.
        if(refreshTokenTable.getExpirationDate().compareTo(new Date(System.currentTimeMillis()) ) < 0 ){
            throw new TokenRefreshmentException("Refresh Token Expired.");
        }

        log.info("token passed validation.");

        /*generating a new refreshToken and setting it up as a cookie response.*/
        response.addCookie(generateRefreshToken(refreshTokenTable.getUser()));

        //generate a new access token.

        return null;
    }
}
