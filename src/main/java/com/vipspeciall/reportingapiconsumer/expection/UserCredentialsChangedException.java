package com.vipspeciall.reportingapiconsumer.expection;

public class UserCredentialsChangedException extends RuntimeException{
    public UserCredentialsChangedException(String message) {
        super(message);
    }
}
