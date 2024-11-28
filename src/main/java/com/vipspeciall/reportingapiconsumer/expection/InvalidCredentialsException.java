package com.vipspeciall.reportingapiconsumer.expection;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
