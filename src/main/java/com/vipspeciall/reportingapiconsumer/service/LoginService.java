package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.LoginRequest;
import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class LoginService {

    private final RedisCacheService redisCacheService;
    private final ExternalAuthService externalAuthService;

    public LoginService(RedisCacheService redisCacheService, ExternalAuthService externalAuthService) {
        this.redisCacheService = redisCacheService;
        this.externalAuthService = externalAuthService;
    }
    public LoginResponse login (LoginRequest request){

        // Daha önce bu email için bir uniqueId oluşturulmuş mu kontrol et
        String existingUniqueId = redisCacheService.getUniqueIdByEmail(request.getEmail());

        String uniqueId;
        if (existingUniqueId != null) {
            // Eğer uniqueId varsa, bunu kullan
            uniqueId = existingUniqueId;
        } else {
            // Eğer uniqueId yoksa, yeni bir uniqueId oluştur ve kaydet
            uniqueId = encrypt(UUID.randomUUID().toString());
            redisCacheService.saveUniqueIdByEmail(request.getEmail(), uniqueId);
        }
        // External API'ye login çağrısı
        LoginResponse response = externalAuthService.login(request.getEmail(), request.getPassword());

        redisCacheService.saveToken(uniqueId, response.getToken());
        response.setUniqueId(uniqueId);

        return response; // Kullanıcıya unique ID döndürülür
    }
    private String encrypt(String data) {
        // Basit bir şifreleme örneği (gerçek uygulamada AES gibi güçlü bir algoritma kullanın)
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}