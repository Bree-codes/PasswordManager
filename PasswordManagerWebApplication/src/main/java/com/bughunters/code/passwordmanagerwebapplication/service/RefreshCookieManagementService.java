package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.RefreshTokenTable;
import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import com.bughunters.code.passwordmanagerwebapplication.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
