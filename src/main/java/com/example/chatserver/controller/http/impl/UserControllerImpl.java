package com.example.chatserver.controller.http.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.http.UserOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.user.UserService;
import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

import jakarta.validation.Valid;

@RestController
public class UserControllerImpl implements UserOperation {
    private final ResponseFactory responseFactory;

    private final UserService userService;

    public UserControllerImpl(ResponseFactory responseFactory, UserService userService) {
        this.responseFactory = responseFactory;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<GeneralResponse<OnboardingResponseDto>> onboard(
            @Valid OnboardingRequestDto onboardingRequestDto) {
        return this.responseFactory.success(this.userService.onboard(onboardingRequestDto));
    }

}
