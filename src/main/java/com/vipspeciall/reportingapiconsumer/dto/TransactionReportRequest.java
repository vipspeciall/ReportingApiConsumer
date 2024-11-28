package com.vipspeciall.reportingapiconsumer.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionReportRequest {

    @NotBlank(message = "uniqueID alani zorunlu (middleware icin)")
    private String uniqueId;

    @NotNull(message = "fromDate alani zorunlu")
    private LocalDate fromDate; // Zorunlu

    @NotNull(message = "toDate alani zorunlu")
    private LocalDate toDate;   // Zorunlu

    @Nullable
    private Integer merchant; // Opsiyonel
    @Nullable
    private Integer acquirer; // Opsiyonel
}
