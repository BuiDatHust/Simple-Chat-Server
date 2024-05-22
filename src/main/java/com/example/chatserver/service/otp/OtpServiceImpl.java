package com.example.chatserver.service.otp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.example.chatserver.entity.*;
import com.example.chatserver.entity.enums.LoginDeviceStatusEnum;
import com.example.chatserver.entity.enums.TypeChatEnum;
import com.example.chatserver.entity.enums.TypeContentMessageEnum;
import com.example.chatserver.framework.InmemoryDatabaseFramework;
import com.example.chatserver.helper.datetime.DateTimeHelper;
import com.example.chatserver.helper.jwt.JwtHelper;
import com.example.chatserver.helper.jwt.TokenGenrationData;
import com.example.chatserver.repository.*;
import com.example.chatserver.service.auth.enums.TokenType;
import com.example.chatserver.service.chat.ChatService;
import com.example.chatserver.service.chat.dto.InitChatData;
import com.example.chatserver.service.otp.dto.request.CheckLoginOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckLoginOtpResponseDto;

import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.entity.enums.UiModeEnum;
import com.example.chatserver.entity.enums.UserChatStatusEnum;
import com.example.chatserver.exception.BaseException;
import com.example.chatserver.framework.SmsFramework;
import com.example.chatserver.helper.GenerateOTP;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.service.otp.dto.request.CheckRegisterOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository; ;
    private final SmsFramework twilioFramework;
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;
    private final LoginDeviceRepository loginDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InmemoryDatabaseFramework inmemoryDatabaseFramework;
    private final ChatService chatService;
    private final MessageRepository messageRepository;
    private final UserSettingRepository userSettingRepository;

    @Value("${otp.expire-time}")
    private Integer otpExpireTime;

    private static String REFRESH_TOKEN_CACHE_TEMPLATE = "RefreshToken_%s_%s";

    @Override
    public void sendOtp(SendOtpRequestDto sendOtpRequestDto) {
        log.info("sendOtp: {}", sendOtpRequestDto);
        
        long currentTime = new Date().getTime();
        String valueOtp = GenerateOTP.generateOTP();
        long expireAt = currentTime + otpExpireTime;
        
        String body = String.format(twilioFramework.getTemplateSms(sendOtpRequestDto.getTypeOtp()), valueOtp);
        Otp otp = Otp.builder()
            .value(valueOtp)
            .isActive(true)
            .phoneNumber(sendOtpRequestDto.getRecipent())
            .expireAt(expireAt)
            .typeOtp(sendOtpRequestDto.getTypeOtp())
            .createdDate(new Date().getTime())
            .build();
        otpRepository.save(otp);
//        otpRepository.inActiveAllOldOtp(sendOtpRequestDto.getTypeOtp().toString(), sendOtpRequestDto.getRecipent());
        twilioFramework.sendSms("admin", sendOtpRequestDto.getRecipent(), body);
    }

    @Override
    public void resendOtp(ResendOtpRequestDto resendOtpRequestDto) {
        if(Objects.equals(resendOtpRequestDto.getTypeOtp(), TypeOtpEnum.REGISTER.toString())){
            handleResendRegisterOtp(resendOtpRequestDto);
        }
        if(Objects.equals(resendOtpRequestDto.getTypeOtp(), TypeOtpEnum.LOGIN.toString())){
            handleResendLoginOtp(resendOtpRequestDto);
        }
    }

    private void handleResendRegisterOtp(ResendOtpRequestDto resendOtpRequestDto) {
        User user = userRepository.findOneByPhoneNumber(resendOtpRequestDto.getPhoneNumber());
        if(Objects.isNull(user) || user.getStatus()!=UserChatStatusEnum.PENDING_OTP){
            throw new BaseException(ResponseStatusCodeEnum.USER_IS_NOT_PENDING_OTP);
        }

        SendOtpRequestDto sendOtpRequestDto = SendOtpRequestDto.builder()
                .typeOtp(TypeOtpEnum.REGISTER)
                .recipent(resendOtpRequestDto.getPhoneNumber())
                .build();
        sendOtp(sendOtpRequestDto);
    }

    public boolean checkOtpRegister(CheckRegisterOtpRequestDto checkRegisterOtpRequestDto){
        Long currentTime= DateTimeHelper.getCurrentTimeUtcMs();
        User existedUser = userRepository.findOneByPhoneNumber(checkRegisterOtpRequestDto.getPhoneNumber());
        if(existedUser==null) {
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }
        if(existedUser.getStatus()!=UserChatStatusEnum.PENDING_OTP){
            throw new BaseException(ResponseStatusCodeEnum.USER_IS_NOT_PENDING_OTP);
        }

        Otp existedOtp = otpRepository.findMatchTypeOtp(checkRegisterOtpRequestDto.getValue(), TypeOtpEnum.REGISTER.toString(), checkRegisterOtpRequestDto.getPhoneNumber());
        if(existedOtp==null) {
            throw new BaseException(ResponseStatusCodeEnum.OTP_NOT_MATCH);
        }
        if(existedOtp.getExpireAt()<=currentTime) {
            throw new BaseException(ResponseStatusCodeEnum.OTP_EXPIRED);
        }

        existedUser.setStatus(UserChatStatusEnum.PENDING_ONBOARDING);
        userRepository.save(existedUser);
        initDataNewUser(existedUser);
        existedOtp.setActive(false);
        otpRepository.save(existedOtp);
        return true;
    }

    @Transactional
    public CheckLoginOtpResponseDto checkOtpLogin(CheckLoginOtpRequestDto checkLoginOtpRequestDto){
        User user = userRepository.findOneByPhoneNumber(checkLoginOtpRequestDto.getPhoneNumber());
        if(Objects.isNull(user)) {
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }
        if(user.getStatus()!=UserChatStatusEnum.ACTIVE) {
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_ACTIVE);
        }

        Otp otp = otpRepository.findMatchTypeOtp(checkLoginOtpRequestDto.getValue(), TypeOtpEnum.LOGIN.toString(), checkLoginOtpRequestDto.getPhoneNumber());
        if(Objects.isNull(otp)){
            throw new BaseException(ResponseStatusCodeEnum.OTP_NOT_MATCH);
        }
        if(otp.getExpireAt() <= DateTimeHelper.getCurrentTimeUtcMs()){
            throw new BaseException(ResponseStatusCodeEnum.OTP_EXPIRED);
        }

        LoginDevice loginDevice = loginDeviceRepository.findOneLoginDeviceByNameAndUserId(checkLoginOtpRequestDto.getDeviceName(), user.getId());
        if(Objects.isNull(loginDevice)) {
            LoginDevice newLoginDevice = LoginDevice.builder()
                    .name(checkLoginOtpRequestDto.getDeviceName())
                    .user(user)
                    .status(LoginDeviceStatusEnum.ACTIVE)
                    .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
                    .build();
            loginDevice = loginDeviceRepository.save(newLoginDevice);
        }

        Long time = DateTimeHelper.getCurrentTimeUtcMs();
        Long expireTimeAccessToken = jwtHelper.getExpireTime(TokenType.ACCESS_TOKEN, time);
        Long expireTimeRefreshToken = jwtHelper.getExpireTime(TokenType.REFRESH_TOKEN, time);
        TokenGenrationData accesTokenGenrationData = TokenGenrationData.builder()
            .deviceName(checkLoginOtpRequestDto.getDeviceName())
            .tokenType(TokenType.ACCESS_TOKEN)
            .phoneNumber(checkLoginOtpRequestDto.getPhoneNumber())
            .time(time)
            .expireTime(expireTimeAccessToken)
            .userId(user.getId())
            .build();
        TokenGenrationData refTokenGenrationData = TokenGenrationData.builder()
            .deviceName(checkLoginOtpRequestDto.getDeviceName())
            .tokenType(TokenType.REFRESH_TOKEN)
            .phoneNumber(checkLoginOtpRequestDto.getPhoneNumber())
            .time(time)
            .expireTime(expireTimeRefreshToken)
            .userId(user.getId())
            .build();
        String accessToken = jwtHelper.generateJwtToken(accesTokenGenrationData);
        String refreshToken = jwtHelper.generateJwtToken(refTokenGenrationData);
        RefreshToken newRefreshToken = RefreshToken.builder()
                .value(refreshToken)
                .isActive(true)
                .expireTime(expireTimeRefreshToken)
                .createdDate(time)
                .loginDevice(loginDevice)
                .build();
        refreshTokenRepository.save(newRefreshToken);
        inmemoryDatabaseFramework.setKey(getRefreshTokenKey(user.getId(), refreshToken), checkLoginOtpRequestDto.getDeviceName());

        return CheckLoginOtpResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String getRefreshTokenKey(Long userId, String refreshToken) {
        return String.format(REFRESH_TOKEN_CACHE_TEMPLATE, userId, refreshToken);
    }

    public void handleResendLoginOtp(ResendOtpRequestDto resendOtpRequestDto) {
        User user = userRepository.findOneByPhoneNumberAndStatus(resendOtpRequestDto.getPhoneNumber(), UserChatStatusEnum.ACTIVE);
        if(Objects.isNull(user)){
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }

        SendOtpRequestDto sendOtpRequestDto = SendOtpRequestDto.builder()
                .typeOtp(TypeOtpEnum.LOGIN)
                .recipent(resendOtpRequestDto.getPhoneNumber())
                .build();
        sendOtp(sendOtpRequestDto);
    }

    private void initDataNewUser(User user) {
        UserSetting userSetting = UserSetting.builder()
            .uiMode(UiModeEnum.WHITE)
            .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
            .build();
        userSettingRepository.save(userSetting);

        InitChatData initChatData = InitChatData.builder()
                .users(new ArrayList<>(List.of(user)))
                .avatar("default")
                .typeChatEnum(TypeChatEnum.SYSTEM)
                .name("System_Chat_"+user.getPhoneNumber())
                .description("This is system message")
                .build();
        Chat chat = chatService.initChat(initChatData);
        Message message = Message.builder()
            .content("Hi, welcome to simple-chat-server! This system message.")
            .contentType(TypeContentMessageEnum.TEXT).chat(chat)
            .createdDate(DateTimeHelper.getCurrentTimeUtcMs())
            .build();
        messageRepository.save(message);
    }
}
