package com.vipspeciall.reportingapiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReportingApiConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportingApiConsumerApplication.class, args);
    }

}
