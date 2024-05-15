package com.example.chatserver.service.otp.dto.request;


import com.example.chatserver.entity.enums.TypeOtpEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendOtpRequestDto {
    @NotNull
    private String recipent;

    @NotNull 
    private TypeOtpEnum typeOtp;
}
