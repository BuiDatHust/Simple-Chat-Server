package com.example.chatserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("${app.application-short-name}/{version}/user")
public interface UserOperation {
    @PostMapping("/onboard")
    ResponseEntity<GeneralResponse<OnboardingResponseDto>> onboard(@Valid @RequestBody OnboardingRequestDto onboardingRequestDto);
}
