package com.example.chatserver.service.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.entity.enums.UserChatStatusEnum;
import com.example.chatserver.exception.BaseException;
import com.example.chatserver.framework.impl.TwilioFrameworkImpl;
import com.example.chatserver.helper.PhoneHelper;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.repository.UserRepository;
import com.example.chatserver.service.auth.dto.request.RegisterByPhoneRequestDto;
import com.example.chatserver.service.auth.dto.response.RegisterByPhoneResponseDto;
import com.example.chatserver.service.otp.OtpService;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OtpService otpService;

    @Autowired
    private TwilioFrameworkImpl twilioFrameworkImpl;

    @Transactional
    @Override
    public RegisterByPhoneResponseDto registerByPhone(RegisterByPhoneRequestDto registerByPhoneRequestDto) {
        log.info("register {}",registerByPhoneRequestDto);

        String phoneNumber = PhoneHelper.getPhoneNumber(registerByPhoneRequestDto.getPhoneNumber(), registerByPhoneRequestDto.getCountryCode());
        User existedUserWithPhone = userRepository.findOneByPhoneNumber(phoneNumber);
        if(existedUserWithPhone!=null) {
            throw new BaseException(ResponseStatusCodeEnum.PHONE_EXISTED);
        }

        User newUser = User.builder()
        .phoneNumber(phoneNumber)
        .countryCode(registerByPhoneRequestDto.getCountryCode())
        .status(UserChatStatusEnum.PENDING_OTP)
        .createdDate(new Date().getTime())
        .build();
        userRepository.save(newUser);

        String body = String.format(twilioFrameworkImpl.getTemplateSms(TypeOtpEnum.REGISTER), null);
        SendOtpRequestDto sendOtpRequestDto = SendOtpRequestDto
            .builder()
            .recipent(phoneNumber)
            .body(body)
            .typeOtp(TypeOtpEnum.REGISTER)
            .build();
        otpService.sendOtp(sendOtpRequestDto);
        return new RegisterByPhoneResponseDto();
    }
}
