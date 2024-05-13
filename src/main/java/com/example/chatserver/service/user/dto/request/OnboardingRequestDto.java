package com.example.chatserver.service.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnboardingRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String countryCode;

    @NotNull
    private String fullname;

    @NotNull
    private String username;
}
