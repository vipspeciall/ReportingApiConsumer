package com.vipspeciall.reportingapiconsumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionListResponse {
    private Integer currentPage;
    private List<DataDTO> data;
    private String first_page_url;
    private int from;
    private String next_page_url;
    private String path;
    private Integer per_page;
    private String prev_page_url;
    private Integer to;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataDTO {
        private FxDTO fx;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // LocalDateTime formatı
        private LocalDateTime updated_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // LocalDateTime formatı
        private LocalDateTime created_at;
        private AcquirerDTO acquirer;
        private TransactionDTO transaction;
        private boolean refundable;
        private CustomerInfoDTO customerInfo;
        private MerchantDTO merchant;
        private IpnDTO ipn; // Optional olarak "ipn" kısmını ekliyoruz
        private Boolean captureAble; // captureAble alanını ekliyoruz

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class FxDTO {
            private MerchantFxDTO merchant;

            @Data
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class MerchantFxDTO {
                private double originalAmount;
                private String originalCurrency;
                private double convertedAmount;
                private String convertedCurrency;
            }
        }

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class AcquirerDTO {
            private int id;
            private String name;
            private String code;
            private String type;
        }

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class TransactionDTO {
            private MerchantTransactionDTO merchant;

            @Data
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class MerchantTransactionDTO {
                private String referenceNo;
                private String status;
                private String operation;
                private String type;
                private String message;
                @JsonInclude(JsonInclude.Include.ALWAYS) // Asıl api de bu field null olsa bile gonderilmis
                private String customData;
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // LocalDateTime formatı
                private LocalDateTime created_at;
                private String transactionId;
            }
        }

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class CustomerInfoDTO {
            private String number; // Bazı durumlarda kart numarası bulunuyor
            private String email;
            private String billingFirstName;
            private String billingLastName;
        }

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class MerchantDTO {
            private int id;
            private String name;
            private boolean allowPartialRefund;
            private boolean allowPartialCapture;
        }

        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class IpnDTO {
            private boolean sent;
            private MerchantIpnDTO merchant;

            @Data
            @JsonInclude(JsonInclude.Include.NON_NULL)
            public static class MerchantIpnDTO {
                private String transactionId;
                private String referenceNo;
                private double amount;
                private String currency;
                private long date;
                private String code;
                private String message;
                private String operation;
                private String type;
                private String status;
                private String customData;
                private String chainId;
                private String paymentType;
                private String descriptor;
                private String token;
                private double convertedAmount;
                private String convertedCurrency;
                private String IPNUrl;
                private String ipnType;
            }
        }
    }
}
