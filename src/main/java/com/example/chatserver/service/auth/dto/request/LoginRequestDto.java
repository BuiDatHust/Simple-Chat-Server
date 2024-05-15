package com.example.chatserver.service.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String deviceName;
}
