package com.vipspeciall.reportingapiconsumer.enums;

public enum ErrorCode {
    DO_NOT_HONOR("Do not honor"),
    INVALID_COUNTRY_ASSOCIATION("Invalid country association"),
    CURRENCY_NOT_ALLOWED("Currency not allowed"),
    THREE_D_SECURE_TRANSPORT_ERROR("3-D Secure Transport Error"),
    TRANSACTION_NOT_PERMITTED_TO_CARDHOLDER("Transaction not permitted to cardholder"),
    INVALID_TRANSACTION("Invalid Transaction"),
    INVALID_CARD("Invalid Card"),
    NOT_SUFFICIENT_FUNDS("Not sufficient funds"),
    INCORRECT_PIN("Incorrect PIN");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Boolean fromMessage(String message) {
        for (ErrorCode type : values()) {
            if (type.getMessage().equals(message)) {
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid value for ExampleEnum: " + message);
    }
}
