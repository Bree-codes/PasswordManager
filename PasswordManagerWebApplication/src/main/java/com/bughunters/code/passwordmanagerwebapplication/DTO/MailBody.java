package com.bughunters.code.passwordmanagerwebapplication.DTO;

import lombok.Builder;

@Builder
public record MailBody(String subject, String text,String to) {
}


