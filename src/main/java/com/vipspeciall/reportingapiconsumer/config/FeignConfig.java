package com.vipspeciall.reportingapiconsumer.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vipspeciall.reportingapiconsumer.expection.ExpiredTokenException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
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
