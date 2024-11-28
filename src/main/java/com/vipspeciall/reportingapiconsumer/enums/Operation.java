package com.vipspeciall.reportingapiconsumer.enums;

public enum Operation {
    DIRECT("DIRECT"),
    REFUND("REFUND"),
    THREE_D("3D"),
    THREE_D_AUTH("3DAUTH"),
    STORED("STORED");

    private final String operationName;

    Operation(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationName() {
        return operationName;
    }


}
