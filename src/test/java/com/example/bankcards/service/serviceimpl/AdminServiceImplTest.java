package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.BankCardsApplication;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.dto.RegisterRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(classes = BankCardsApplication.class)
class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl service;

    @BeforeEach
    void setUp() {}

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void register() {
        RegisterRequestDto requestDto = new RegisterRequestDto(
                "John", "pass", "John", "Mark"
        );
        AuthenticationResponseDto dto = service.register(requestDto);

        Assertions.assertNotNull(dto);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllUsers() {
        List<User> users = service.getAllUsers();

        Assertions.assertNotNull(users);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteAllUsers() {
        Assertions.assertDoesNotThrow(() -> {
            service.deleteAllUsers();
        });
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getUserByUsername() {
        User user = service.getUserByUsername("Ibra");

        Assertions.assertNotNull(user);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteUserByUsername() {
        Assertions.assertDoesNotThrow(() -> {
            service.deleteUserByUsername("Ibra");
        });
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void getAllCards() {
        List<Card> cards = service.getAllCards();

        Assertions.assertNotNull(cards);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void createCard() {
        Card card = service.createCard("Ibra");

        Assertions.assertNotNull(card);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void blockCard() {
        Card card = service.blockCard("5469380043745632");

        Assertions.assertNotNull(card);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void unblockCard() {
        service.blockCard("5469380043745632");
        Card card = service.unblockCard("5469380043745632");

        Assertions.assertNotNull(card);
    }

    @Test
    @Sql({"/initialize/data.sql"})
    @After("/initialize/clean.sql")
    void deleteCard() {
        Assertions.assertDoesNotThrow(() -> {
            service.deleteCard("5469380043745632");
        });
    }
}