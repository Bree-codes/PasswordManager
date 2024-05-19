package com.bughunters.code.passwordmanagerwebapplication.response;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RefreshTokenResponse {

    private Long userId;

    private String token;

    private String username;

}
