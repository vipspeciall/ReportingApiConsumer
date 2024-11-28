package com.vipspeciall.reportingapiconsumer.expection;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
