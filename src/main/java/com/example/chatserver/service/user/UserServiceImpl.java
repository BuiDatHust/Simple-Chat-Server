package com.example.chatserver.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.UserChatStatusEnum;
import com.example.chatserver.exception.BaseException;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.repository.UserRepository;
import com.example.chatserver.service.user.dto.request.OnboardingRequestDto;
import com.example.chatserver.service.user.dto.response.OnboardingResponseDto;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public OnboardingResponseDto onboard(OnboardingRequestDto onboardingRequestDto) {
        User existedUserWithPhone = userRepository.findOneByPhoneNumber(onboardingRequestDto.getPhoneNumber());
        boolean isExistUser = existedUserWithPhone!=null;
        if(!isExistUser) {
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }
        if(existedUserWithPhone.getStatus()!=UserChatStatusEnum.PENDING_ONBOARDING) {
            throw new BaseException(ResponseStatusCodeEnum.USER_IS_NOT_PENDING_ONBOARD);
        }

        User existedUserWithUsername = userRepository.findOneByUsername(onboardingRequestDto.getUsername());
        if(existedUserWithUsername!=null) {
            throw new BaseException(ResponseStatusCodeEnum.USERNAME_EXISTED);
        }

        existedUserWithPhone.setStatus(UserChatStatusEnum.ACTIVE);
        existedUserWithPhone.setCountryCode(onboardingRequestDto.getCountryCode());
        existedUserWithPhone.setFullname(onboardingRequestDto.getFullname());
        existedUserWithPhone.setUsername(onboardingRequestDto.getUsername());
        userRepository.save(existedUserWithPhone);

        return new OnboardingResponseDto();
    }

}
