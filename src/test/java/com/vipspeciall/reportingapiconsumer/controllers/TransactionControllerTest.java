package com.vipspeciall.reportingapiconsumer.controllers;


import com.vipspeciall.reportingapiconsumer.config.SecurityConfig;
import com.vipspeciall.reportingapiconsumer.controller.TransactionController;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportResponse;
import com.vipspeciall.reportingapiconsumer.dto.TransactionListRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionListResponse;
import com.vipspeciall.reportingapiconsumer.enums.Status;
import com.vipspeciall.reportingapiconsumer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import(SecurityConfig.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private TransactionReportRequest reportRequest;
    private TransactionReportResponse reportResponse;

    private TransactionListRequest listRequest;
    private TransactionListResponse listResponse;

    @BeforeEach
    void setUp() {
        reportRequest = new TransactionReportRequest();
        reportRequest.setFromDate(LocalDate.of(2023, 1, 1)); // Tarih nesnesi atandı
        reportRequest.setToDate(LocalDate.of(2023, 1, 31)); // Tarih nesnesi atandı

        // Transaction Report Response setup
        reportResponse = new TransactionReportResponse();
        reportResponse.setStatus(Status.APPROVED);

        TransactionReportResponse.Response responseData = new TransactionReportResponse.Response();
        responseData.setCount(5);
        responseData.setTotal(1000);
        responseData.setCurrency("USD");

        reportResponse.setResponse(List.of(responseData));


        listRequest = new TransactionListRequest();
        listRequest.setFromDate(LocalDate.of(2023, 1, 1));
        listRequest.setToDate(LocalDate.of(2023, 1, 31));

        listResponse = new TransactionListResponse();
        listResponse.setCurrentPage(1);
    }

    @Test
    void shouldReturn200ForTransactionReport() throws Exception {
        // Arrange
        Mockito.when(transactionService.getTransactionReport(any(TransactionReportRequest.class)))
                .thenReturn(reportResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "uniqueId": "random_unique_id",
                                    "fromDate": "2023-01-01",
                                    "toDate": "2023-01-31"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.response[0].count").value(5))
                .andExpect(jsonPath("$.response[0].total").value(1000))
                .andExpect(jsonPath("$.response[0].currency").value("USD"));
    }

    @Test
    void shouldReturn200ForTransactionList() throws Exception {
        // Arrange
        Mockito.when(transactionService.getTransactionList(any(TransactionListRequest.class)))
                .thenReturn(listResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "uniqueId": "random_unique_id",
                                    "fromDate": "2023-01-01",
                                    "toDate": "2023-01-31"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(1));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactions/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "fromDate": "",
                                    "toDate": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
