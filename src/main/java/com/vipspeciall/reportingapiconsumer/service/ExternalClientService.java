package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.GetClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "externalClient", url = "https://sandbox-reporting.rpdpymnt.com/api/v3/client")
public interface ExternalClientService {

    @PostMapping
    GetClientResponse getClient(
            @RequestHeader("Authorization") String token,
            @RequestParam("transactionId") String transactionId
    );
}
