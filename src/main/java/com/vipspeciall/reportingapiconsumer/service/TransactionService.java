package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.TransactionListRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionListResponse;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final ExternalTransactionService externalTransactionService;
    private final TokenService tokenService;

    public TransactionService(TokenService tokenService, ExternalTransactionService externalTransactionService) {
        this.externalTransactionService = externalTransactionService;
        this.tokenService = tokenService;
    }

    public TransactionReportResponse getTransactionReport(TransactionReportRequest transactionReportRequest) {
        // External API çağrısı sırasında token kullanılıyor
        String token = tokenService.getAccessToken(transactionReportRequest.getUniqueId());
        return externalTransactionService.getTransactionReport(
                token,
                transactionReportRequest.getFromDate(),
                transactionReportRequest.getToDate(),
                transactionReportRequest.getMerchant(),
                transactionReportRequest.getAcquirer());
    }


    public TransactionListResponse getTransactionList(TransactionListRequest transactionListRequest) {

        String token = tokenService.getAccessToken(transactionListRequest.getUniqueId());
        // External API çağrısı
        return externalTransactionService.getTransactionList(
                token,
                transactionListRequest.getFromDate() != null ? transactionListRequest.getFromDate().toString() : null, // Feign in tarih donuturme formatinda bir sorun yasandigi icin gecici olarak string kullanildi
                transactionListRequest.getToDate() != null ? transactionListRequest.getToDate().toString() : null, // Feign in tarih donuturme formatinda bir sorun yasandigi icin gecici olarak string kullanildi
                transactionListRequest.getStatus() != null ? transactionListRequest.getStatus().toString() : null,
                transactionListRequest.getOperation() != null ? transactionListRequest.getOperation().toString() : null,
                transactionListRequest.getMerchantId(),
                transactionListRequest.getAcquirerId(),
                transactionListRequest.getPaymentMethod() != null ? transactionListRequest.getPaymentMethod().toString() : null,
                transactionListRequest.getErrorCode() != null ? transactionListRequest.getErrorCode().toString() : null,
                transactionListRequest.getFilterField() != null ? transactionListRequest.getFilterField().toString() : null,
                transactionListRequest.getFilterValue(),
                transactionListRequest.getPage()
        );
    }
}