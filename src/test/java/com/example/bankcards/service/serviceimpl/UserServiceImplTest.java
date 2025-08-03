package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.entity.Card;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(classes = BankCardsApplication.class)
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl service;

    private CardResponseDto dto;

    @BeforeEach
    void setUp() {
        dto = new CardResponseDto();
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void loadUserByUsername() {
        UserDetails details = service.loadUserByUsername("Ibra");

        Assertions.assertNotNull(details);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllCards() {
        List<Card> list = service.getAllCards("Ibra");

        Assertions.assertNotNull(list);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void render() {
        dto = service.render("5469380043745632",
                "5469380043745798", 100);

        Assertions.assertNotNull(dto);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void block() {
        dto = service.block("5469380043745632");

        Assertions.assertNotNull(dto);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getBalance() {
        dto = service.getBalance("5469380043745632");

        Assertions.assertNotNull(dto);
    }
}