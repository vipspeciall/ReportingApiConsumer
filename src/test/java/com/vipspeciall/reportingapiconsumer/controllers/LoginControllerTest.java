package com.vipspeciall.reportingapiconsumer.controllers;

import com.vipspeciall.reportingapiconsumer.config.SecurityConfig;
import com.vipspeciall.reportingapiconsumer.controller.LoginController;
import com.vipspeciall.reportingapiconsumer.dto.LoginRequest;
import com.vipspeciall.reportingapiconsumer.dto.LoginResponse;
import com.vipspeciall.reportingapiconsumer.exception.InvalidCredentialsException;
import com.vipspeciall.reportingapiconsumer.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private LoginRequest validRequest;
    private LoginResponse validResponse;

    @BeforeEach
    void setUp() {
        validRequest = new LoginRequest();
        validRequest.setEmail("test@example.com");
        validRequest.setPassword("password123");

        validResponse = new LoginResponse();
        validResponse.setToken("validToken");
        validResponse.setUniqueId("unique123");
    }

    @Test
    void shouldReturn200AndTokenWhenLoginSuccessful() throws Exception {
        // Arrange
        Mockito.when(loginService.login(any(LoginRequest.class)))
                .thenReturn(validResponse);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("validToken"))
                .andExpect(jsonPath("$.uniqueId").value("unique123"));
    }

    @Test
    void shouldReturn401WhenLoginFails() throws Exception {
        // Arrange
        Mockito.when(loginService.login(any(LoginRequest.class)))
                .thenThrow(new InvalidCredentialsException("Invalid Credentials"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "wrong@example.com",
                                    "password": "wrongpassword"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid Credentials"));
    }

    @Test
    void shouldReturn400WhenRequestIsInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "",
                                    "password": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
