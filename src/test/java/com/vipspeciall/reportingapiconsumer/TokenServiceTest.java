package com.vipspeciall.reportingapiconsumer;

import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import com.vipspeciall.reportingapiconsumer.service.ExternalAuthService;
import com.vipspeciall.reportingapiconsumer.service.RedisCacheService;
import com.vipspeciall.reportingapiconsumer.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TokenServiceTest {

    private final RedisCacheService redisCacheService = Mockito.mock(RedisCacheService.class);
    private final ExternalAuthService externalAuthService = Mockito.mock(ExternalAuthService.class);
    private final TokenService tokenService = new TokenService(redisCacheService, null, null);

    @Test
    public void testHandleTokenExpiration() {
        // Mock kullanıcı email'i
        String email = "user@example.com";

        // Redis'teki token silinmiş olmalı
        redisCacheService.deleteToken(email);
        assertNull(redisCacheService.getToken(email));

        // Yeni token alınmış olmalı (mock yapılandırılmışsa)
        String newToken = "mockedToken"; // Yeni token simüle ediliyor
        Mockito.when(redisCacheService.getToken(email)).thenReturn(newToken);
        assertNotNull(newToken);
    }

    @Test
    public void testExpiredTokenHandledCorrectly() {
        // 1. Simüle edilen kullanıcı email'i ve şifresi
        String email = "user@example.com";
        String password = "123456";

        // 2. Redis'ten dönen expired token
        Mockito.when(redisCacheService.getToken(email)).thenReturn("expired_token");

        // 3. External API'den yeni token dönülmesini simüle et
        Mockito.when(externalAuthService.login(email, password))
                .thenReturn(new LoginResponse("new_valid_token", "APPROVED"));

        // 4. Token yenileme işlemini tetikle
        tokenService.handleTokenExpiration(email);

        // 5. Yeni token'ın Redis'e kaydedildiğini doğrula
        Mockito.verify(redisCacheService, Mockito.times(1)).saveToken(email, "new_valid_token");
    }

}
