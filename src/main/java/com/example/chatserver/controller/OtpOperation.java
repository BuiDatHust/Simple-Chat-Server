package com.example.chatserver.controller;

import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;

public interface OtpOperation {
    @PostMapping("/verify")
    ResponseEntity<GeneralResponse<CheckOtpResponseDto>> verifyOtp(@RequestBody @Valid CheckOtpRequestDto registerByPhoneRequestDto);

    @PostMapping("/resend")
    ResponseEntity<GeneralResponse<ResendOtpResponseDto>> resendOtp(@RequestBody @Valid ResendOtpRequestDto resendOtpRequestDto);
}
