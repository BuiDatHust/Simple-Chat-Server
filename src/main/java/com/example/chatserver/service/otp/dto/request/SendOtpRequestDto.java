package com.example.chatserver.service.otp.dto.request;

import javax.validation.constraints.NotNull;

import com.example.chatserver.entity.enums.TypeOtpEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendOtpRequestDto {
    @NotNull
    private String recipent;

    @NotNull
    private String body;

    @NotNull 
    private TypeOtpEnum typeOtp;
}
