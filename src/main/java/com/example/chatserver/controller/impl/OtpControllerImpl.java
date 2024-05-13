package com.example.chatserver.controller.impl;

import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.OtpOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.otp.OtpService;
import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;

@RestController
@RequestMapping("/otp")
public class OtpControllerImpl implements OtpOperation {
    @Autowired
    private OtpService otpService;

    @Autowired
    private ResponseFactory responseFactory;

    @Override
    @PostMapping("/verify")
    public ResponseEntity<GeneralResponse<CheckOtpResponseDto>> verifyOtp(@Valid @RequestBody CheckOtpRequestDto checkOtpRequestDto) {
        return responseFactory.success(otpService.checkOtp(checkOtpRequestDto));
    }

    @Override
    @PostMapping("/resend")
    public ResponseEntity<GeneralResponse<ResendOtpResponseDto>> resendOtp(@Valid @RequestBody ResendOtpRequestDto resendOtpRequestDto) {
        otpService.resendOtp(resendOtpRequestDto);
        return responseFactory.success(new ResendOtpResponseDto());
    }
}
