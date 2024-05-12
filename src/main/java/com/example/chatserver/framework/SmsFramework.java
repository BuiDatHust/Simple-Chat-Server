package com.example.chatserver.framework;

import com.example.chatserver.entity.enums.TypeOtpEnum;

public interface SmsFramework {
    void sendSms(String sender, String recipent, String body);

    String getTemplateSms(TypeOtpEnum typeOtp);
}
