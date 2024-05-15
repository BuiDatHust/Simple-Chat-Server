package com.example.chatserver.controller;

import com.example.chatserver.service.otp.dto.request.CheckLoginOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckLoginOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.service.otp.dto.request.CheckRegisterOtpRequestDto;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("${app.application-short-name}/{version}/otp")
public interface OtpOperation {
    @PostMapping("/register-verification")
    ResponseEntity<GeneralResponse<CheckOtpResponseDto>> verifyRegisterOtp(@RequestBody @Valid CheckRegisterOtpRequestDto registerByPhoneRequestDto);

    @PostMapping("/resend")
    ResponseEntity<GeneralResponse<ResendOtpResponseDto>> resendOtp(@RequestBody @Valid ResendOtpRequestDto resendOtpRequestDto);

    @PostMapping("/login-verification")
    ResponseEntity<GeneralResponse<CheckLoginOtpResponseDto>> verifyLoginOtp(@RequestBody @Valid CheckLoginOtpRequestDto cLoginOtpRequestDto);
}
