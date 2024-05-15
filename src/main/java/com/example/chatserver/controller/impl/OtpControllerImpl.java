package com.example.chatserver.controller.impl;

import com.example.chatserver.service.otp.dto.request.CheckLoginOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckLoginOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.OtpOperation;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.service.otp.OtpService;
import com.example.chatserver.service.otp.dto.request.CheckRegisterOtpRequestDto;

@RestController
public class OtpControllerImpl implements OtpOperation {
    private final OtpService otpService;

    private final ResponseFactory responseFactory;

    public OtpControllerImpl(OtpService otpService, ResponseFactory responseFactory) {
        this.otpService = otpService;
        this.responseFactory = responseFactory;
    }

    @Override
    public ResponseEntity<GeneralResponse<CheckOtpResponseDto>> verifyRegisterOtp(@Valid @RequestBody CheckRegisterOtpRequestDto checkRegisterOtpRequestDto) {
        otpService.checkOtpRegister(checkRegisterOtpRequestDto);
        return responseFactory.success(null);
    }

    @Override
    public ResponseEntity<GeneralResponse<ResendOtpResponseDto>> resendOtp(@Valid @RequestBody ResendOtpRequestDto resendOtpRequestDto) {
        otpService.resendOtp(resendOtpRequestDto);
        return responseFactory.success(new ResendOtpResponseDto());
    }

    @Override
    public ResponseEntity<GeneralResponse<CheckLoginOtpResponseDto>> verifyLoginOtp(CheckLoginOtpRequestDto checkLoginOtpRequestDto) {
        return responseFactory.success(otpService.checkOtpLogin(checkLoginOtpRequestDto));
    }
}
