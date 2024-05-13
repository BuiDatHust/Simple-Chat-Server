package com.example.chatserver.service.otp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckOtpResponseDto {
    private boolean isVerified;
}
