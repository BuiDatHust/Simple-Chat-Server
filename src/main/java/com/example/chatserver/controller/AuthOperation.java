package com.example.chatserver.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;

public interface AuthOperation {
    @PostMapping("/register-by-phone")
    ResponseEntity<GeneralResponse<RegisterByPhoneResponseDto>> registerByPhone(@Valid @RequestBody RegisterByPhoneRequestDto registerByPhoneRequestDto);
}
