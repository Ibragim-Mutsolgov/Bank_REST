package com.example.bankcards.controller;

import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.serviceimpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    public final UserServiceImpl service;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Просмотр карт
    @GetMapping("/get_all_cards")
    public ResponseEntity<List<Card>> getAllCards(@RequestBody CardDto cardDto) {
        logger.info("Запрос списка карт для пользователя: " + cardDto.getUsername());
        return ResponseEntity.ok(service.getAllCards(cardDto.getUsername()));
    }

    //Перевод
    @PostMapping("/render")
    public ResponseEntity<CardResponseDto> render(@RequestBody CardDto cardDto) {
        logger.info("Запрос перевода для пользователя: " + cardDto.getUsername());
        return ResponseEntity.ok(service.render(cardDto.getCardNumberSender(),  cardDto.getCardNumberRecipient(), cardDto.getBalance()));
    }

    // Запрос на блок
    @PostMapping("/block")
    public ResponseEntity<CardResponseDto> block(@RequestBody CardDto cardDto) {
        logger.info("Запрос на блокировку карты для пользователя: " + cardDto.getUsername());
        service.block(cardDto.getCardNumber());
        return ResponseEntity.ok().build();
    }

    // Смотрит баланс
    @GetMapping("/get_balance")
    public ResponseEntity<CardResponseDto> getBalance(@RequestBody CardDto cardDto) {
        logger.info("Запрос баланса по карте: " + cardDto.getCardNumber());
        return ResponseEntity.ok(service.getBalance(cardDto.getCardNumber()));
    }
}
