package com.example.chatserver.helper.response;

public interface ResponseStatusCodeEnum {
    ResponseStatusCode SUCCESS = ResponseStatusCode.builder().code("00").httpCode(200).build();
    ResponseStatusCode BUSINESS_ERROR = ResponseStatusCode.builder().code("BS1").httpCode(400).build();
    ResponseStatusCode VALIDATION_ERROR = ResponseStatusCode.builder().code("BS2").httpCode(400).build();
    ResponseStatusCode INTERNAL_GENERAL_SERVER_ERROR = ResponseStatusCode.builder().code("BS3").httpCode(500).build();
    ResponseStatusCode ERROR_BODY_CLIENT = ResponseStatusCode.builder().code("BS4").httpCode(400).build();
    ResponseStatusCode ERROR_BODY_REQUIRED = ResponseStatusCode.builder().code("BS5").httpCode(400).build();
    ResponseStatusCode PHONE_EXISTED = ResponseStatusCode.builder().code("BS6").httpCode(400).build();
    ResponseStatusCode USERNAME_EXISTED = ResponseStatusCode.builder().code("BS7").httpCode(400).build();
    ResponseStatusCode OTP_EXPIRED = ResponseStatusCode.builder().code("BS8").httpCode(400).build();
    ResponseStatusCode OTP_NOT_MATCH = ResponseStatusCode.builder().code("BS9").httpCode(400).build();
    ResponseStatusCode USER_NOT_EXIST = ResponseStatusCode.builder().code("BS10").httpCode(400).build();
    ResponseStatusCode USER_IS_NOT_PENDING_OTP = ResponseStatusCode.builder().code("BS11").httpCode(400).build();
    ResponseStatusCode USER_IS_NOT_PENDING_ONBOARD = ResponseStatusCode.builder().code("BS12").httpCode(400).build();
    ResponseStatusCode USER_NOT_ACTIVE = ResponseStatusCode.builder().code("BS13").httpCode(400).build();
    ResponseStatusCode DEVICE_NOT_EXIST = ResponseStatusCode.builder().code("BS14").httpCode(400).build();
    ResponseStatusCode FORBIDDEN_TOKEN_INVALID = ResponseStatusCode.builder().code("BS15").httpCode(400).build();
}