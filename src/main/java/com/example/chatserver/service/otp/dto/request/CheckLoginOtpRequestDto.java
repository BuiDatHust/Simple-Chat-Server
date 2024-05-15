package com.example.chatserver.service.otp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class CheckLoginOtpRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String value;

    @NotNull
    private String deviceName;
}
