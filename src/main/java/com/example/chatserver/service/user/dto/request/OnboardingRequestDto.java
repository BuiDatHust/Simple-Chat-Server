package com.example.chatserver.service.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OnboardingRequestDto {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String fullname;

    @NotBlank
    private String username;
}
