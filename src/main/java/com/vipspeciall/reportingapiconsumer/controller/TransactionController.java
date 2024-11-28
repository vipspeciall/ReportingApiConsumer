package com.vipspeciall.reportingapiconsumer.controller;

import com.vipspeciall.reportingapiconsumer.dto.TransactionListRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionListResponse;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportRequest;
import com.vipspeciall.reportingapiconsumer.dto.TransactionReportResponse;
import com.vipspeciall.reportingapiconsumer.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/report")
    public TransactionReportResponse getTransactionReport(@Valid @RequestBody TransactionReportRequest transactionReportRequest) {

        return transactionService.getTransactionReport(transactionReportRequest);

    }

    @PostMapping("/list")  // Transaction Query Api si
    public TransactionListResponse getTransactionList(@RequestBody TransactionListRequest transactionListRequest){
        return transactionService.getTransactionList(transactionListRequest);
    }
}