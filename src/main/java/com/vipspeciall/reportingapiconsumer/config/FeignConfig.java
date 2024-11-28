package com.vipspeciall.reportingapiconsumer.config;

import com.vipspeciall.reportingapiconsumer.exception.ExpiredTokenException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            if (response.status() == 401) {
                return new ExpiredTokenException("Token süresi doldu.");
            }
            return new Exception("External API'den hata alındı: " + response.status());
        };
    }

}
