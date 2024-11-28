package com.vipspeciall.reportingapiconsumer.services;

import com.vipspeciall.reportingapiconsumer.service.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisCacheServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisCacheService redisCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void shouldSaveUniqueIdByEmailSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String uniqueId = "unique123";

        doNothing().when(valueOperations).set(email, uniqueId, 24, TimeUnit.DAYS);

        // Act
        redisCacheService.saveUniqueIdByEmail(email, uniqueId);

        // Assert
        verify(valueOperations, times(1)).set(email, uniqueId, 24, TimeUnit.DAYS);
    }

    @Test
    void shouldGetUniqueIdByEmailSuccessfully() {
        // Arrange
        String email = "test@example.com";
        String expectedUniqueId = "unique123";

        when(valueOperations.get(email)).thenReturn(expectedUniqueId);

        // Act
        String actualUniqueId = redisCacheService.getUniqueIdByEmail(email);

        // Assert
        assertNotNull(actualUniqueId, "Unique ID should not be null");
        assertEquals(expectedUniqueId, actualUniqueId, "Unique ID should match the expected value");
    }

    @Test
    void shouldSaveTokenSuccessfully() {
        // Arrange
        String uniqueId = "unique123";
        String token = "test-token";

        doNothing().when(valueOperations).set(uniqueId, token, 10, TimeUnit.MINUTES);

        // Act
        redisCacheService.saveToken(uniqueId, token);

        // Assert
        verify(valueOperations, times(1)).set(uniqueId, token, 10, TimeUnit.MINUTES);
    }

    @Test
    void shouldGetTokenSuccessfully() {
        // Arrange
        String uniqueId = "unique123";
        String expectedToken = "test-token";

        when(valueOperations.get(uniqueId)).thenReturn(expectedToken);

        // Act
        String actualToken = redisCacheService.getToken(uniqueId);

        // Assert
        assertNotNull(actualToken, "Token should not be null");
        assertEquals(expectedToken, actualToken, "Token should match the expected value");
    }

    @Test
    void shouldReturnNullWhenUniqueIdDoesNotExist() {
        // Arrange
        String email = "test@example.com";

        when(valueOperations.get(email)).thenReturn(null);

        // Act
        String actualUniqueId = redisCacheService.getUniqueIdByEmail(email);

        // Assert
        assertNull(actualUniqueId, "Unique ID should be null if it does not exist");
    }

    @Test
    void shouldReturnNullWhenTokenDoesNotExist() {
        // Arrange
        String uniqueId = "unique123";

        when(valueOperations.get(uniqueId)).thenReturn(null);

        // Act
        String actualToken = redisCacheService.getToken(uniqueId);

        // Assert
        assertNull(actualToken, "Token should be null if it does not exist");
    }
}
