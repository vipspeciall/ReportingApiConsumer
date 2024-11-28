package com.vipspeciall.reportingapiconsumer.controller;

import com.vipspeciall.reportingapiconsumer.dto.GetClientRequest;
import com.vipspeciall.reportingapiconsumer.dto.GetClientResponse;
import com.vipspeciall.reportingapiconsumer.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    GetClientResponse getClient(@Valid @RequestBody GetClientRequest request) {
        return clientService.getClient(request);
    }
}
