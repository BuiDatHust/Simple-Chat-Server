package com.example.chatserver.service.otp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckLoginOtpResponseDto {
    private String accessToken;

    private String refreshToken;
}
