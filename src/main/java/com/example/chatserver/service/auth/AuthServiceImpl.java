package com.example.chatserver.service.auth;

import java.util.Date;
import java.util.Objects;

import com.example.chatserver.constant.TokenParams;
import com.example.chatserver.entity.LoginDevice;
import com.example.chatserver.repository.LoginDeviceRepository;
import com.example.chatserver.repository.RefreshTokenRepository;
import com.example.chatserver.service.auth.dto.request.LoginRequestDto;
import com.example.chatserver.service.auth.dto.request.LogoutRequestDto;
import com.example.chatserver.service.auth.dto.response.LoginResponseDto;
import com.example.chatserver.service.auth.dto.response.LogoutResponseDto;

import com.example.chatserver.service.otp.dto.request.LogoutDataDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.LoginDeviceStatusEnum;
import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.entity.enums.UserChatStatusEnum;
import com.example.chatserver.exception.BaseException;
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
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final LoginDeviceRepository loginDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceImpl(UserRepository userRepository, OtpService otpService, LoginDeviceRepository loginDeviceRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.loginDeviceRepository = loginDeviceRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

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

        SendOtpRequestDto sendOtpRequestDto = SendOtpRequestDto
            .builder()
            .recipent(phoneNumber)
            .typeOtp(TypeOtpEnum.REGISTER)
            .build();
        otpService.sendOtp(sendOtpRequestDto);
        return new RegisterByPhoneResponseDto();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findOneByPhoneNumberAndStatus(loginRequestDto.getPhoneNumber(), UserChatStatusEnum.ACTIVE);
        if(Objects.isNull(user)){
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }
        LoginDevice loginDevice = loginDeviceRepository.findOneLoginDeviceByNameAndUserId(loginRequestDto.getDeviceName(), user.getId());
        if(!Objects.isNull(loginDevice)) {
//            todo: create message system otp
        }

        SendOtpRequestDto sendOtpRequestDto = SendOtpRequestDto
                .builder()
                .recipent(loginRequestDto.getPhoneNumber())
                .typeOtp(TypeOtpEnum.LOGIN)
                .build();
        otpService.sendOtp(sendOtpRequestDto);
        return new LoginResponseDto();
    }

    @Override
    public LogoutResponseDto logout(LogoutDataDto logoutDataDto) {
        LoginDevice loginDevice = loginDeviceRepository.findOneLoginDeviceByNameAndUserId(logoutDataDto.getDeviveName(), logoutDataDto.getUserId());
        if(Objects.isNull(loginDevice)) {
            throw new BaseException(ResponseStatusCodeEnum.DEVICE_NOT_EXIST);
        }
        loginDevice.setStatus(LoginDeviceStatusEnum.INACTIVE);
        loginDeviceRepository.save(loginDevice);
        refreshTokenRepository.deleteAllByLoginDevice(loginDevice);
        return null;
    }

}
