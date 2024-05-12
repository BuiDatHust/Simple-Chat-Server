package com.example.chatserver.service.otp;

import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;

public interface OtpService {
    void sendOtp(SendOtpRequestDto sendOtpRequestDto);
    boolean checkOtp(CheckOtpRequestDto checkOtpRequestDto);
}
