package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = BankCardsApplication.class)
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService service;

    private AuthenticationRequestDto request;

    @BeforeEach
    void setUp() {
        request = new AuthenticationRequestDto();
        request.setUsername("admin");
        request.setPassword("admin");
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void authenticate() {
        AuthenticationResponseDto dto = service.authenticate(request);

        Assertions.assertNotNull(dto);

        request.setUsername("a");

        dto = service.authenticate(request);

        Assertions.assertNull(dto);
    }
}