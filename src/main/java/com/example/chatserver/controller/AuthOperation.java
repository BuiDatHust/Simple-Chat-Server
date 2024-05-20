package com.example.chatserver.controller;

import com.example.chatserver.service.auth.dto.request.LoginRequestDto;
import com.example.chatserver.service.auth.dto.request.LogoutRequestDto;
import com.example.chatserver.service.auth.dto.response.LoginResponseDto;
import com.example.chatserver.service.auth.dto.response.LogoutResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("${app.application-short-name}/{version}/auth")
public interface AuthOperation {
    @PostMapping("/register-by-phone")
    ResponseEntity<GeneralResponse<RegisterByPhoneResponseDto>> registerByPhone(@Valid @RequestBody RegisterByPhoneRequestDto registerByPhoneRequestDto);

    @PostMapping("/login")
    ResponseEntity<GeneralResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto);

    @PostMapping("/logout")
    ResponseEntity<GeneralResponse<LogoutResponseDto>> logout(@Valid @RequestBody LogoutRequestDto logoutRequestDto, HttpServletRequest httpServletRequest);
}
