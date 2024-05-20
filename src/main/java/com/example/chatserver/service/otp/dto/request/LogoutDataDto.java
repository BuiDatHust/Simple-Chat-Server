package com.example.chatserver.service.otp.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutDataDto {
    private String phoneNumber;
    private String deviveName;
    private Long userId;
}
