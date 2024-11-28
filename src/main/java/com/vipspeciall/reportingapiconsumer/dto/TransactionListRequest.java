package com.vipspeciall.reportingapiconsumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vipspeciall.reportingapiconsumer.enums.ErrorCode;
import com.vipspeciall.reportingapiconsumer.enums.FilterField;
import com.vipspeciall.reportingapiconsumer.enums.PaymentMethod;
import com.vipspeciall.reportingapiconsumer.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionListRequest {

    @NotBlank(message = "uniqueID alani zorunlu (middleware icin)")
    private String uniqueId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // LocalDate formatı
    private LocalDate fromDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // LocalDate formatı
    private LocalDate toDate;
    private Status status;
    private String operation;
    private Integer merchantId;
    private Integer acquirerId;
    private PaymentMethod paymentMethod;
    private ErrorCode errorCode;
    private FilterField filterField;
    private String filterValue;
    private Integer page;

}
