package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.TransactionListResponse;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "externalTransactionReport", url = "https://sandbox-reporting.rpdpymnt.com/api/v3/")

public interface ExternalTransactionService {

    @PostMapping("transactions/report")
    TransactionReportResponse getTransactionReport(
            @RequestHeader("Authorization") String token,
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate,
            @RequestParam(value = "merchant", required = false) Integer merchant,
            @RequestParam(value = "acquirer", required = false) Integer acquirer

    );

    @PostMapping("transaction/list")
    TransactionListResponse getTransactionList(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "operation", required = false) String operation,
            @RequestParam(value = "merchantId", required = false) Integer merchantId,
            @RequestParam(value = "acquirerId", required = false) Integer acquirerId,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            @RequestParam(value = "filterField", required = false) String filterField,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "page", required = false) Integer page
            );
}
