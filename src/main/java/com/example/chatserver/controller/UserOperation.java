package com.example.chatserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

import jakarta.validation.Valid;

public interface UserOperation {
    ResponseEntity<GeneralResponse<OnboardingResponseDto>> onboard(@Valid @RequestBody OnboardingRequestDto onboardingRequestDto);
}
