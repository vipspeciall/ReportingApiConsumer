package com.vipspeciall.reportingapiconsumer.controller;

import com.vipspeciall.reportingapiconsumer.dto.LoginRequest;
import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import com.vipspeciall.reportingapiconsumer.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
