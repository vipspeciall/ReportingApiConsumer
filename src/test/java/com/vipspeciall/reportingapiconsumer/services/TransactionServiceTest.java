package com.vipspeciall.reportingapiconsumer.services;

import com.vipspeciall.reportingapiconsumer.dto.*;
import com.vipspeciall.reportingapiconsumer.enums.Status;
import com.vipspeciall.reportingapiconsumer.exception.ExpiredTokenException;
import com.vipspeciall.reportingapiconsumer.exception.ExternalApiException;
import com.vipspeciall.reportingapiconsumer.service.ExternalTransactionService;
import com.vipspeciall.reportingapiconsumer.service.TokenService;
import com.vipspeciall.reportingapiconsumer.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;
    private ExternalTransactionService externalTransactionService;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        externalTransactionService = mock(ExternalTransactionService.class);
        tokenService = mock(TokenService.class);
        transactionService = new TransactionService(tokenService, externalTransactionService);
    }

    @Test
    void shouldReturnTransactionReportSuccessfully() {
        // Arrange
        TransactionReportRequest request = new TransactionReportRequest();
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 1, 31));
        request.setUniqueId("unique123");

        String token = "Bearer test-token";
        TransactionReportResponse expectedResponse = new TransactionReportResponse();
        expectedResponse.setStatus(Status.APPROVED);

        TransactionReportResponse.Response responseData = new TransactionReportResponse.Response();
        responseData.setCount(5);
        responseData.setTotal(1000);
        responseData.setCurrency("USD");
        expectedResponse.setResponse(List.of(responseData));

        // Mock TokenService to return a valid token
        when(tokenService.getAccessToken("unique123")).thenReturn(token);

        // Mock ExternalTransactionService response
        when(externalTransactionService.getTransactionReport(
                eq(token),
                eq(LocalDate.of(2023, 1, 1)),
                eq(LocalDate.of(2023, 1, 31)),
                any(),
                any()
        )).thenReturn(expectedResponse);

        // Act
        TransactionReportResponse actualResponse = transactionService.getTransactionReport(request);

        // Assert
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(Status.APPROVED, actualResponse.getStatus());
        assertEquals(5, actualResponse.getResponse().get(0).getCount());
        assertEquals(1000, actualResponse.getResponse().get(0).getTotal());
        assertEquals("USD", actualResponse.getResponse().get(0).getCurrency());
    }

    @Test
    void shouldThrowExceptionWhenTransactionReportFails() {
        // Arrange
        TransactionReportRequest request = new TransactionReportRequest();
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 1, 31));
        request.setUniqueId("unique123");

        String token = "Bearer test-token";

        // Mock TokenService to return a valid token
        when(tokenService.getAccessToken("unique123")).thenReturn(token);

        // Mock ExternalTransactionService to throw an exception
        when(externalTransactionService.getTransactionReport(
                eq(token),
                eq(LocalDate.of(2023, 1, 1)),
                eq(LocalDate.of(2023, 1, 31)),
                any(),
                any()
        )).thenThrow(new ExternalApiException("External API error"));

        // Act & Assert
        ExternalApiException exception = assertThrows(ExternalApiException.class,
                () -> transactionService.getTransactionReport(request));
        assertEquals("External API error", exception.getMessage());
    }

    @Test
    void shouldReturnTransactionListSuccessfully() {
        // Arrange
        TransactionListRequest request = new TransactionListRequest();
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 1, 31));
        request.setUniqueId("unique123");

        String token = "Bearer test-token";

        TransactionListResponse expectedResponse = new TransactionListResponse();
        expectedResponse.setCurrentPage(1);

        // Mock TokenService to return a valid token
        when(tokenService.getAccessToken("unique123")).thenReturn(token);

        // Mock ExternalTransactionService response
        when(externalTransactionService.getTransactionList(
                eq(token),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(expectedResponse);

        // Act
        TransactionListResponse actualResponse = transactionService.getTransactionList(request);

        // Assert
        assertNotNull(actualResponse, "Response should not be null");
        assertEquals(1, actualResponse.getCurrentPage());
    }

    @Test
    void shouldThrowExpiredTokenExceptionWhenTokenIsExpired() {
        // Arrange
        TransactionListRequest request = new TransactionListRequest();
        request.setFromDate(LocalDate.of(2023, 1, 1));
        request.setToDate(LocalDate.of(2023, 1, 31));
        request.setUniqueId("unique123");

        // Mock TokenService to throw ExpiredTokenException
        when(tokenService.getAccessToken("unique123")).thenThrow(new ExpiredTokenException("Token expired"));

        // Act & Assert
        ExpiredTokenException exception = assertThrows(ExpiredTokenException.class,
                () -> transactionService.getTransactionList(request));
        assertEquals("Token expired", exception.getMessage());
    }
}
