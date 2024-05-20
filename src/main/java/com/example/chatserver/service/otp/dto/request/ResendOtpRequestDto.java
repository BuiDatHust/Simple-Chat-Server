package com.example.chatserver.service.otp.dto.request;

import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.validation.annotation.EnumValidator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendOtpRequestDto {
    @NotNull
    private String phoneNumber;

    @NotNull
    @EnumValidator(enumClazz = TypeOtpEnum.class)
    private String typeOtp;
}
