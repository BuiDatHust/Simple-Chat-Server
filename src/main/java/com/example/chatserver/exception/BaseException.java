package com.example.chatserver.exception;

import com.example.chatserver.helper.response.GeneralResponse;
import com.example.chatserver.helper.response.ResponseStatusCode;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private final ResponseStatusCode responseStatusCode;
    private GeneralResponse dataResponse;

    public BaseException(ResponseStatusCode responseStatusCode, GeneralResponse dataResponse) {
        this.responseStatusCode=responseStatusCode;
        this.dataResponse=dataResponse;
    }

    public BaseException(ResponseStatusCode responseStatusCode) {
        this.responseStatusCode=responseStatusCode;
    }
}
