package com.example.chatserver.service.auth.dto.request;

import javax.validation.constraints.NotNull;

import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.validation.EnumValidator;

import lombok.Data;

@Data
public class RegisterByPhoneRequestDto {
    @NotNull
    private String phoneNumber;

    @EnumValidator(enumClazz = TypeOtpEnum.class)
    @NotNull
    private String countryCode;
}
