package com.example.chatserver.service.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterByPhoneRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String countryCode;
}
