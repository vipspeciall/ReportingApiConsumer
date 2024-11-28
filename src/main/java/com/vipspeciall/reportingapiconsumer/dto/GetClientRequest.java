package com.vipspeciall.reportingapiconsumer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetClientRequest {

    @NotNull(message = "uniqueId alani bos birakilamaz")
    private String uniqueId;
    @NotNull(message = "uniqueId alani bos birakilamaz")
    private String transactionId;
}
