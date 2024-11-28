package com.vipspeciall.reportingapiconsumer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email bos birakilamaz!")
    @Email(message = "Hatali mail formati")
    private String email;

    @NotBlank(message = "Sifre alanı bos birakilamaz")
    private String password;

}
