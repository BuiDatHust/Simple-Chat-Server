package com.example.chatserver.service.auth.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OnboardRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    private String countryCode;

    @NotNull
    private String fullname;

    @NotNull
    private String username;
}
