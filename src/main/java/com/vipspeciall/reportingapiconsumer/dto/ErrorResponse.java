package com.vipspeciall.reportingapiconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String status;
    private String message;
}
