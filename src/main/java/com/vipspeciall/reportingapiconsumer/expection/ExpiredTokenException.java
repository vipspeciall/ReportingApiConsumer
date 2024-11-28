package com.vipspeciall.reportingapiconsumer.expection;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String message) {
        super(message);
    }
}
