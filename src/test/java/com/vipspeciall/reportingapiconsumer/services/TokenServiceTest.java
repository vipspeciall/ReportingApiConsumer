package com.vipspeciall.reportingapiconsumer.services;

import com.vipspeciall.reportingapiconsumer.exception.TokenNotFoundException;
import com.vipspeciall.reportingapiconsumer.service.ExternalAuthService;
import com.vipspeciall.reportingapiconsumer.service.RedisCacheService;
import com.vipspeciall.reportingapiconsumer.service.TokenService;
import com.vipspeciall.reportingapiconsumer.repository.UserCredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    private TokenService tokenService;
    private RedisCacheService redisCacheService;
    private ExternalAuthService externalAuthService;
    private UserCredentialsRepository userCredentialsRepository;

    @BeforeEach
    void setUp() {
        redisCacheService = mock(RedisCacheService.class);
        externalAuthService = mock(ExternalAuthService.class);
        userCredentialsRepository = mock(UserCredentialsRepository.class);
        tokenService = new TokenService(redisCacheService, externalAuthService, userCredentialsRepository);
    }

    @Test
    void shouldGetAccessTokenSuccessfully() {
        // Arrange
        String uniqueId = "unique123";
        String expectedToken = "test-token";

        when(redisCacheService.getToken(uniqueId)).thenReturn(expectedToken);

        // Act
        String actualToken = tokenService.getAccessToken(uniqueId);

        // Assert
        assertNotNull(actualToken, "Access token should not be null");
        assertEquals(expectedToken, actualToken, "Access token should match the expected value");
    }

    @Test
    void shouldThrowTokenNotFoundExceptionIfTokenDoesNotExist() {
        // Arrange
        String uniqueId = "unique123";

        when(redisCacheService.getToken(uniqueId)).thenReturn(null);

        // Act & Assert
        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> tokenService.getAccessToken(uniqueId));
        assertEquals("Token bulunamadı. Lütfen yeniden giriş yapın.", exception.getMessage());
    }
}
