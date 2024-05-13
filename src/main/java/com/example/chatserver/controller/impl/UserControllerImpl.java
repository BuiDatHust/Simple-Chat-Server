package com.example.chatserver.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.UserOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.user.UserService;
import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserOperation {
    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private UserService userService;

    @PostMapping("/onboard")
    @Override
    public ResponseEntity<GeneralResponse<OnboardingResponseDto>> onboard(
            @Valid OnboardingRequestDto onboardingRequestDto) {
        return this.responseFactory.success(this.userService.onboard(onboardingRequestDto));
    }

}
