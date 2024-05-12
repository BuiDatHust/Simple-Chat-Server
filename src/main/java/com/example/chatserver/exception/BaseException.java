package com.example.chatserver.exception;

import com.example.chatserver.helper.response.ResponseStatusCode;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {
    private ResponseStatusCode responseStatusCode;

    public BaseException(ResponseStatusCode responseStatusCode) {
        this.responseStatusCode=responseStatusCode;
    }
}
