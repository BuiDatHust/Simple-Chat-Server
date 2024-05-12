package com.example.chatserver.service.otp.dto.request;

import javax.validation.constraints.NotNull;

import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.validation.EnumValidator;

import lombok.Data;

@Data
public class CheckOtpRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    @EnumValidator(enumClazz = TypeOtpEnum.class)
    private String typeOtp;

    @NotNull
    private String value; 
}
