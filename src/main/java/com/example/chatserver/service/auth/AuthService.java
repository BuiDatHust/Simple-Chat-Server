package com.example.chatserver.service.auth;

import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;

import org.springframework.stereotype.Service;

import com.example.chatserver.service.auth.dto.request.OnboardRequestDto;

@Service
public interface AuthService {
    RegisterByPhoneResponseDto registerByPhone(RegisterByPhoneRequestDto registerByPhone);

    void onboard(OnboardRequestDto registerRequest);
}
