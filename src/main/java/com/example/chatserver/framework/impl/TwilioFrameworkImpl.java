package com.example.chatserver.framework.impl;

import org.springframework.stereotype.Service;

import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.framework.SmsFramework;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TwilioFrameworkImpl implements SmsFramework {

    @Override
    public void sendSms(String sender, String recipent, String body) {
        log.info("sendSms: sender-{}, recipent-{}, body-{}", body);
    }
    
    @Override
    public String getTemplateSms(TypeOtpEnum typeOtp) {
        return switch (typeOtp) {
            case REGISTER ->
                    "Day la ma code dang ky tai khoan. \n vui long khong chia se cho bat ki ai.\n Ma code la: %s";
            case LOGIN -> "Day la ma code dang dang nhap tai khoan. \n vui long khong chia se cho bat ki ai.\n Ma code la: %s";
            case LOGIN_NEW_DEVICE -> "";
            default -> "";
        };
    }
}
