package com.example.chatserver.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;

public interface OtpOperation {
    @PostMapping("/verify")
    ResponseEntity<GeneralResponse<Boolean>> verifyOtp(@RequestBody @Valid CheckOtpRequestDto registerByPhoneRequestDto);
}
