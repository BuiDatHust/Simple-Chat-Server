package com.example.chatserver.service.otp;

import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;

public interface OtpService {
    void sendOtp(SendOtpRequestDto sendOtpRequestDto);
    CheckOtpResponseDto checkOtp(CheckOtpRequestDto checkOtpRequestDto);
    void resendOtp(ResendOtpRequestDto resendOtpRequestDto);
}
