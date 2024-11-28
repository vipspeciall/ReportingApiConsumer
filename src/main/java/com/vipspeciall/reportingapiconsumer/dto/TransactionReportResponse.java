package com.vipspeciall.reportingapiconsumer.dto;


import com.vipspeciall.reportingapiconsumer.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class TransactionReportResponse {

    private Status status;
    private List<Response> response;

    @Data
    public static class Response {
        private int count;
        private int total;
        private String currency;
    }

}
