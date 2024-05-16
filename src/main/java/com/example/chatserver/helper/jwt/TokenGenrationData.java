package com.example.chatserver.helper.jwt;

import com.example.chatserver.service.auth.enums.TokenType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenGenrationData {
    private TokenType tokenType;
    private String phoneNumber;
    private String deviceName;
    private Long userId;
    private Long time;
    private Long expireTime;
}
