package com.example.chatserver.controller.http.impl;

import com.example.chatserver.constant.TokenParams;
import com.example.chatserver.service.auth.dto.request.LoginRequestDto;
import com.example.chatserver.service.auth.dto.request.LogoutRequestDto;
import com.example.chatserver.service.auth.dto.response.LoginResponseDto;
import com.example.chatserver.service.auth.dto.response.LogoutResponseDto;
import com.example.chatserver.service.otp.dto.request.LogoutDataDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.http.AuthOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.auth.AuthService;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;

@RestController
@Slf4j
public class AuthControllerImpl implements AuthOperation {
    private final AuthService authService;

    private final ResponseFactory responseFactory;

    public AuthControllerImpl(AuthService authService, ResponseFactory responseFactory) {
        this.authService = authService;
        this.responseFactory = responseFactory;
    }

    public ResponseEntity<GeneralResponse<RegisterByPhoneResponseDto>> registerByPhone(
        @Valid @RequestBody RegisterByPhoneRequestDto registerByPhoneRequestDto) {
        return responseFactory.success(authService.registerByPhone(registerByPhoneRequestDto));
    }

    @Override
    public ResponseEntity<GeneralResponse<LoginResponseDto>> login(LoginRequestDto loginRequestDto) {
        return responseFactory.success(authService.login(loginRequestDto));
    }

    @Override
    public ResponseEntity<GeneralResponse<LogoutResponseDto>> logout(LogoutRequestDto logoutRequestDto, HttpServletRequest request) {
        log.info("logout, {}", logoutRequestDto);

        LogoutDataDto logoutDataDto = LogoutDataDto.builder()
                .phoneNumber((String) request.getAttribute(TokenParams.phoneNumber))
                .deviveName((String) request.getAttribute(TokenParams.deviceName))
                .userId((Long) request.getAttribute(TokenParams.userId))
                .build();
        return responseFactory.success(authService.logout(logoutDataDto));
    }
}
