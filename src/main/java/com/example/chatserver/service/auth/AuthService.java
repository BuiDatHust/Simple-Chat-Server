package com.example.chatserver.service.auth;

import com.example.chatserver.service.auth.dto.request.LoginRequestDto;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.LoginResponseDto;
import com.example.chatserver.service.auth.dto.response.LogoutResponseDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;

import com.example.chatserver.service.otp.dto.request.LogoutDataDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    RegisterByPhoneResponseDto registerByPhone(RegisterByPhoneRequestDto registerByPhone);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    LogoutResponseDto logout(LogoutDataDto logoutDataDto);
}
