package com.vipspeciall.reportingapiconsumer.services;

import com.vipspeciall.reportingapiconsumer.dto.GetClientRequest;
import com.vipspeciall.reportingapiconsumer.dto.GetClientResponse;
import com.vipspeciall.reportingapiconsumer.service.ClientService;
import com.vipspeciall.reportingapiconsumer.service.ExternalClientService;
import com.vipspeciall.reportingapiconsumer.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientService clientService;
    private ExternalClientService externalClientService;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        externalClientService = mock(ExternalClientService.class);
        tokenService = mock(TokenService.class);
        clientService = new ClientService(externalClientService, tokenService);
    }

    @Test
    void shouldReturnClientDetailsSuccessfully() {
        // Arrange
        GetClientRequest request = new GetClientRequest();
        request.setUniqueId("unique123");
        request.setTransactionId("txn789");

        String token = "Bearer test-token";

        GetClientResponse expectedResponse = new GetClientResponse();
        GetClientResponse.CustomerInfo customerInfo = new GetClientResponse.CustomerInfo();
        customerInfo.setId(1);
        customerInfo.setEmail("michael@gmail.com");
        expectedResponse.setCustomerInfo(customerInfo);

        when(tokenService.getAccessToken("unique123")).thenReturn(token);
        when(externalClientService.getClient(eq(token), eq("txn789"))).thenReturn(expectedResponse);

        // Act
        GetClientResponse actualResponse = clientService.getClient(request);

        // Assert
        assertNotNull(actualResponse);
        assertNotNull(actualResponse.getCustomerInfo());
        assertNotNull(actualResponse.getCustomerInfo().getEmail());
    }
}
