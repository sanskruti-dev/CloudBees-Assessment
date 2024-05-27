package com.cloudbees.assessment.util;

public class CloudbeesException extends RuntimeException {
    private String message;
    private int httpErrorCode;

    public CloudbeesException(String message, int httpErrorCode) {
        this.message = message;
        this.httpErrorCode = httpErrorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public void setHttpErrorCode(int httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
    }
}
