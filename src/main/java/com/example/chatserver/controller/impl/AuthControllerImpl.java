package com.example.chatserver.controller.impl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.AuthOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.auth.AuthService;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;

@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthOperation {
    @Autowired
    private AuthService authService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping(path = "/register-by-phone", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GeneralResponse<RegisterByPhoneResponseDto>> registerByPhone(
        @Valid @RequestBody RegisterByPhoneRequestDto registerByPhoneRequestDto) {
        return responseFactory.success(authService.registerByPhone(registerByPhoneRequestDto));
    }
}
