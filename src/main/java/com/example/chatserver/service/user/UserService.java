package com.example.chatserver.service.user;

import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

public interface UserService {
    OnboardingResponseDto onboard(OnboardingRequestDto onboardingRequestDto);
}
