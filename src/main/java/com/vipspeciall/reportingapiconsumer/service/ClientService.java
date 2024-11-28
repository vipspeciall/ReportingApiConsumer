package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.GetClientRequest;
import com.vipspeciall.reportingapiconsumer.dto.GetClientResponse;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ExternalClientService externalClientService;
    private final TokenService tokenService;

    public ClientService(ExternalClientService externalClientService, TokenService tokenService) {
        this.externalClientService = externalClientService;
        this.tokenService = tokenService;
    }

    public GetClientResponse getClient(GetClientRequest getClientRequest){

        String token = tokenService.getAccessToken(getClientRequest.getUniqueId());
        return externalClientService.getClient(token, getClientRequest.getTransactionId());

    }
}
