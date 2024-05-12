package com.example.chatserver.controller.impl;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatserver.controller.OtpOperation;
import com.example.chatserver.entity.enums.TypeOtpEnum;
import com.example.chatserver.exception.BaseException;
import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseFactory;
import com.example.chatserver.helper.response.ResponseStatusCodeEnum;
import com.example.chatserver.service.otp.OtpService;
import com.example.chatserver.service.otp.dto.request.CheckOtpRequestDto;

@RestController
@RequestMapping("/otp")
public class OtpControllerImpl implements OtpOperation {
    @Autowired
    private OtpService otpService;

    @Autowired
    private ResponseFactory responseFactory;

    @Override
    @PostMapping("/verify")
    public ResponseEntity<GeneralResponse<Boolean>> verifyOtp(@Valid CheckOtpRequestDto checkOtpRequestDto) {
        TypeOtpEnum otpEnum = TypeOtpEnum.valueOf(checkOtpRequestDto.getTypeOtp());
        if(otpEnum==null) {
            throw new BaseException(ResponseStatusCodeEnum.BUSINESS_ERROR);
        }
        return responseFactory.success(otpService.checkOtp(checkOtpRequestDto));
    }
}
