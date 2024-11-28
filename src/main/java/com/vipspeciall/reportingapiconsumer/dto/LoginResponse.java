package com.vipspeciall.reportingapiconsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String uniqueId;
    private String token;
    private String status;

    public LoginResponse(String newValidToken, String approved) {
    }
}
