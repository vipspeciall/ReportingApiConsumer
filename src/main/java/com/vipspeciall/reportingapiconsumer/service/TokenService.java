package com.vipspeciall.reportingapiconsumer.service;

import com.vipspeciall.reportingapiconsumer.dto.LoginRequest;
import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import com.vipspeciall.reportingapiconsumer.entity.UserCredentials;
import com.vipspeciall.reportingapiconsumer.expection.TokenNotFoundException;
import com.vipspeciall.reportingapiconsumer.repository.UserCredentialsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenService {

    private final RedisCacheService redisCacheService;
    private final ExternalAuthService externalAuthService;
    private final UserCredentialsRepository userCredentialsRepository;

    public TokenService(RedisCacheService redisCacheService, ExternalAuthService externalAuthService, UserCredentialsRepository userCredentialsRepository) {
        this.redisCacheService = redisCacheService;
        this.externalAuthService = externalAuthService;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public String getAccessToken(String uniqueId) {
        String token = redisCacheService.getToken(uniqueId);

        if (token == null) {
            // Token yoksa 401 Unauthorized dön
            throw new TokenNotFoundException("Token bulunamadı. Lütfen yeniden giriş yapın.");
        }

        return token;
    }

    public void handleTokenExpiration(String uniqueId) {
        // Redis'teki token'i sil
        redisCacheService.deleteToken(uniqueId);

    }

    private String fetchNewToken(String uniqueId) {
        Optional<UserCredentials> userOpt = userCredentialsRepository.findByUniqueID(uniqueId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Kullanıcı bilgileri bulunamadı. Yeniden login olunması gerekiyor.");
        }

        UserCredentials user = userOpt.get();

        LoginResponse response = externalAuthService.login(user.getEmail(), user.getPassword());

        // Yeni token'i Redis'e kaydet
        redisCacheService.saveToken(uniqueId, response.getToken());

        return response.getToken();
    }
}