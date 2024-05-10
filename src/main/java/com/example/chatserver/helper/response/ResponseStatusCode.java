package com.example.chatserver.helper.response;

public class ResponseStatusCode {
    private final String code;
    private final int httpCode;

    public ResponseStatusCode(String code, int httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }

    public String toString() {
        return "ResponseStatus{code='" + this.code + '\'' + "httpCode='" + this.httpCode + '\'' + '}';
    }

    public static ResponseStatusCodeBuilder builder() {
        return new ResponseStatusCodeBuilder();
    }

    public String getCode() {
        return this.code;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public static class ResponseStatusCodeBuilder {
        private String code;
        private int httpCode;

        ResponseStatusCodeBuilder() {
        }

        public ResponseStatusCodeBuilder code(final String code) {
            this.code = code;
            return this;
        }

        public ResponseStatusCodeBuilder httpCode(final int httpCode) {
            this.httpCode = httpCode;
            return this;
        }

        public ResponseStatusCode build() {
            return new ResponseStatusCode(this.code, this.httpCode);
        }

        public String toString() {
            return "ResponseStatusCode.ResponseStatusCodeBuilder(code=" + this.code + ", httpCode=" + this.httpCode + ")";
        }
    }
}
