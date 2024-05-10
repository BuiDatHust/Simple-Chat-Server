package com.example.chatserver.helper.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralResponse<T> {
    @JsonProperty("status")
    private ResponseStatus status;
    @JsonProperty("data")
    private T data;

    public GeneralResponse(T data) {
        this.data = data;
    }

    public String toString() {
        return "{status=" + this.status + ", data=" + this.data.toString() + '}';
    }

    public ResponseStatus getStatus() {
        return this.status;
    }

    public T getData() {
        return this.data;
    }

    @JsonProperty("status")
    public void setStatus(final ResponseStatus status) {
        this.status = status;
    }

    @JsonProperty("data")
    public void setData(final T data) {
        this.data = data;
    }

    public GeneralResponse() {
    }
}
