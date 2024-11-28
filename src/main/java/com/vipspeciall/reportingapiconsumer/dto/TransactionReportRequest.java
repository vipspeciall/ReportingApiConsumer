package com.vipspeciall.reportingapiconsumer.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionReportRequest {
    private String uniqueId;
    private LocalDate fromDate; // Zorunlu
    private LocalDate toDate;   // Zorunlu
    @Nullable
    private Integer merchant; // Opsiyonel
    @Nullable
    private Integer acquirer; // Opsiyonel
}
