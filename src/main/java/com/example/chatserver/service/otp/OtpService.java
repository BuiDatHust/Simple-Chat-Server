package com.example.chatserver.service.otp;

import com.example.chatserver.service.otp.dto.request.CheckLoginOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.CheckRegisterOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckLoginOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;

public interface OtpService {
    void sendOtp(SendOtpRequestDto sendOtpRequestDto);
    void resendOtp(ResendOtpRequestDto resendOtpRequestDto);
    boolean checkOtpRegister(CheckRegisterOtpRequestDto checkRegisterOtpRequestDto);
    CheckLoginOtpResponseDto checkOtpLogin(CheckLoginOtpRequestDto checkLoginOtpRequestDto);
}
