package com.example.chatserver.service.otp;

import java.util.Date;
import java.util.Objects;

import com.example.chatserver.service.otp.dto.request.ResendOtpRequestDto;
import com.example.chatserver.service.otp.dto.response.CheckOtpResponseDto;
import com.example.chatserver.service.otp.dto.response.ResendOtpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.chatserver.entity.Otp;
import com.example.chatserver.entity.User;
import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.entity.enums.UserChatStatusEnum;
import com.example.chatserver.exception.BaseException;
import com.example.chatserver.framework.SmsFramework;
import com.example.chatserver.helper.GenerateOTP;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.repository.OtpRepository;
import com.example.chatserver.repository.UserRepository;
import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;
import com.example.chatserver.service.otp.dto.request.SendOtpRequestDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository; ;
    
    @Autowired
    private SmsFramework twilioFramework;

    @Autowired
    private UserRepository userRepository;

    @Value("${otp.expire-time}")
    private Integer otpExpireTime;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public void sendOtp(SendOtpRequestDto sendOtpRequestDto) {
        log.info("sendOtp: {}", sendOtpRequestDto);
        
        long currentTime = new Date().getTime();

        String valueOtp = GenerateOTP.generateOTP();
        long expireAt = currentTime + otpExpireTime;
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
        twilioFramework.sendSms("admin", sendOtpRequestDto.getRecipent(), sendOtpRequestDto.getBody());
    }

    @Override
    public CheckOtpResponseDto checkOtp(CheckOtpRequestDto checkOtpRequestDto) {
        Long currentTime = new Date().getTime();
        boolean result = false;
        if(Objects.equals(checkOtpRequestDto.getTypeOtp(), TypeOtpEnum.REGISTER.toString())){
            result = handleCheckOtpRegister(checkOtpRequestDto, currentTime);
        }
        
        return CheckOtpResponseDto.builder().isVerified(result).build();
    }

    @Override
    public void resendOtp(ResendOtpRequestDto resendOtpRequestDto) {
        if(Objects.equals(resendOtpRequestDto.getTypeOtp(), TypeOtpEnum.REGISTER.toString())){
            handleResendRegisterOtp(resendOtpRequestDto);
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
                .body(twilioFramework.getTemplateSms(TypeOtpEnum.REGISTER))
                .build();
        sendOtp(sendOtpRequestDto);
    }

    private boolean handleCheckOtpRegister(CheckOtpRequestDto checkOtpRequestDto, Long currentTime){
        User existedUser = userRepository.findOneByPhoneNumber(checkOtpRequestDto.getPhoneNumber());
        if(existedUser==null) {
            throw new BaseException(ResponseStatusCodeEnum.USER_NOT_EXIST);
        }
        if(existedUser.getStatus()!=UserChatStatusEnum.PENDING_OTP){
            throw new BaseException(ResponseStatusCodeEnum.USER_IS_NOT_PENDING_OTP);
        }

        Otp existedOtp = otpRepository.findMatchTypeOtp(checkOtpRequestDto.getValue(), TypeOtpEnum.REGISTER.toString(), checkOtpRequestDto.getPhoneNumber());
        if(existedOtp==null) {
            throw new BaseException(ResponseStatusCodeEnum.OTP_NOT_MATCH);
        }
        if(existedOtp.getExpireAt()<=currentTime) {
            throw new BaseException(ResponseStatusCodeEnum.OTP_EXPIRED);
        }

        existedUser.setStatus(UserChatStatusEnum.PENDING_ONBOARDING);
        userRepository.save(existedUser);
        existedOtp.setActive(false);
        otpRepository.save(existedOtp);
        return true;
    }
}
