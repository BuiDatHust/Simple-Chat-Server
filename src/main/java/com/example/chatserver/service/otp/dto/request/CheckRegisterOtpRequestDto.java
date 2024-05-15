package com.example.chatserver.service.otp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckRegisterOtpRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String value;
}
