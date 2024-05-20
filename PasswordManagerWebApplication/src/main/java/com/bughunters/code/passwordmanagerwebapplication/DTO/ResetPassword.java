package com.bughunters.code.passwordmanagerwebapplication.DTO;

import lombok.Builder;

@Builder
public record ResetPassword(String password,String confirmPassword) {
}
