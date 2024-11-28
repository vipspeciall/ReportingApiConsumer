package com.vipspeciall.reportingapiconsumer.controllers;

import com.vipspeciall.reportingapiconsumer.config.SecurityConfig;
import com.vipspeciall.reportingapiconsumer.controller.ClientController;
import com.vipspeciall.reportingapiconsumer.dto.GetClientRequest;
import com.vipspeciall.reportingapiconsumer.dto.GetClientResponse;
import com.vipspeciall.reportingapiconsumer.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
@Import(SecurityConfig.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void shouldReturnClientDetailsSuccessfully() throws Exception {
        // Arrange
        GetClientResponse.CustomerInfo customerInfo = new GetClientResponse.CustomerInfo();
        customerInfo.setId(1);
        customerInfo.setEmail("michael@gmail.com");
        customerInfo.setBillingFirstName("Michael");
        customerInfo.setBillingLastName("Kara");
        customerInfo.setBillingCity("Antalya");

        GetClientResponse response = new GetClientResponse();
        response.setCustomerInfo(customerInfo);

        Mockito.when(clientService.getClient(any(GetClientRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "uniqueId": "unique123",
                            "transactionId": "txn789"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerInfo.id").value(1))
                .andExpect(jsonPath("$.customerInfo.email").value("michael@gmail.com"))
                .andExpect(jsonPath("$.customerInfo.billingFirstName").value("Michael"))
                .andExpect(jsonPath("$.customerInfo.billingLastName").value("Kara"))
                .andExpect(jsonPath("$.customerInfo.billingCity").value("Antalya"));
    }
}
