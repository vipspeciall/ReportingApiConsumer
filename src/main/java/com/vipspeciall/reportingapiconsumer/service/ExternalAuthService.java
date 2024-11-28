package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "externalAuth", url = "https://sandbox-reporting.rpdpymnt.com/api/v3/merchant/user")
public interface ExternalAuthService {

    @PostMapping("/login")
    LoginResponse login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    );


}
