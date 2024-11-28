package com.vipspeciall.reportingapiconsumer.services;

import com.vipspeciall.reportingapiconsumer.dto.LoginRequest;
import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import com.vipspeciall.reportingapiconsumer.exception.InvalidCredentialsException;
import com.vipspeciall.reportingapiconsumer.service.ExternalAuthService;
import com.vipspeciall.reportingapiconsumer.service.LoginService;
import com.vipspeciall.reportingapiconsumer.service.RedisCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private ExternalAuthService externalAuthService;

    @Mock
    private RedisCacheService redisCacheService;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        LoginResponse expectedResponse = new LoginResponse();
        expectedResponse.setToken("test-token");

        when(externalAuthService.login(request.getEmail(), request.getPassword())).thenReturn(expectedResponse);
        doNothing().when(redisCacheService).saveToken(anyString(), eq("test-token"));

        // Act
        LoginResponse actualResponse = loginService.login(request);

        // Assert
        assertNotNull(actualResponse, "LoginResponse should not be null");
        assertEquals("test-token", actualResponse.getToken(), "Token should match the expected value");
        assertNotNull(actualResponse.getUniqueId(), "Unique ID should not be null");
        verify(redisCacheService, times(1)).saveToken(anyString(), eq("test-token"));
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenLoginFails() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        when(externalAuthService.login(request.getEmail(), request.getPassword()))
                .thenThrow(new InvalidCredentialsException("Invalid credentials"));

        // Act & Assert
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                () -> loginService.login(request));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(redisCacheService, never()).saveToken(anyString(), anyString());
    }

    @Test
    void shouldHandleEmptyTokenGracefully() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        LoginResponse response = new LoginResponse();
        response.setToken(null); // No token returned

        when(externalAuthService.login(request.getEmail(), request.getPassword())).thenReturn(response);

        // Act
        LoginResponse actualResponse = loginService.login(request);

        // Assert
        assertNotNull(actualResponse, "LoginResponse should not be null");
        assertNull(actualResponse.getToken(), "Token should be null if not provided");
        verify(redisCacheService, never()).saveToken(anyString(), anyString());
    }
}
