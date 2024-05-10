package com.example.chatserver.helper.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class ResponseStatus implements Serializable {
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("responseTime")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    )
    private Date responseTime;
    @JsonProperty("displayMessage")
    private String displayMessage;

    public ResponseStatus(String code, boolean setMessageImplicitly) {
        this.setCode(code, setMessageImplicitly);
    }

    public void setCode(String code, boolean setMessageImplicitly) {
        this.code = code;
//        if (setMessageImplicitly) {
//            this.message = Translator.toLocale(code);
//        }

        this.displayMessage = this.message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.setCode(code, true);
    }

    public String getMessage() {
        return this.message;
    }

    public Date getResponseTime() {
        return this.responseTime;
    }

    public String getDisplayMessage() {
        return this.displayMessage;
    }

    @JsonProperty("message")
    public void setMessage(final String message) {
        this.message = message;
    }

    @JsonProperty("responseTime")
    public void setResponseTime(final Date responseTime) {
        this.responseTime = responseTime;
    }

    @JsonProperty("displayMessage")
    public void setDisplayMessage(final String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public ResponseStatus() {
    }

    public ResponseStatus(final String code, final String message, final Date responseTime, final String displayMessage) {
        this.code = code;
        this.message = message;
        this.responseTime = responseTime;
        this.displayMessage = displayMessage;
    }

    public String toString() {
        return "ResponseStatus(code=" + this.getCode() + ", message=" + this.getMessage() + ", responseTime=" + this.getResponseTime() + ", displayMessage=" + this.getDisplayMessage() + ")";
    }
}
