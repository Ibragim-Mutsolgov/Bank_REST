package com.example.bankcards.controller;

import com.example.bankcards.dto.AdminRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.dto.CardDto;
import com.example.bankcards.dto.RegisterRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.serviceimpl.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl service;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    // Создание нового пользователя
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) {
        logger.info("Запрос на создание нового пользователя: " + request.getUsername());
        return ResponseEntity.ok(service.register(request));
    }

    // Выдать список всех пользователей
    @GetMapping("/get_all_users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Запрос списка пользователей");
        return ResponseEntity.ok().body(service.getAllUsers());
    }

    //Удалить всех пользователей и банковские карты
    @DeleteMapping("/delete_all_users")
    public ResponseEntity<?> deleteAllUsers() {
        service.deleteAllUsers();
        logger.info("Удаление всех пользователей");
        return ResponseEntity.ok().build();
    }

    // Выдать одного пользователя
    @GetMapping("/get_user")
    public ResponseEntity<User> getUser(@RequestBody AdminRequestDto adminRequestDto) {
        logger.info("Запрос пользователя: " + adminRequestDto.getUsername());
        return ResponseEntity.ok().body(service.getUserByUsername(adminRequestDto.getUsername()));
    }

    // Удалить пользователя и его карты
    @DeleteMapping("/delete_user")
    public ResponseEntity<?> deleteUser(@RequestBody AdminRequestDto adminRequestDto) {
        service.deleteUserByUsername(adminRequestDto.getUsername());
        logger.info("Удаление пользователя: " + adminRequestDto.getUsername());
        return ResponseEntity.ok().build();
    }

    // Создать карту пользователю
    @PostMapping("/create_card")
    public ResponseEntity<Card> createCard(@RequestBody AdminRequestDto adminRequestDto) {
        logger.info("Создание карты для пользователя: " + adminRequestDto.getUsername());
        return ResponseEntity.ok().body(service.createCard(adminRequestDto.getUsername()));
    }

    // Заблокировать карту пользователя
    @PutMapping("/block_card")
    public ResponseEntity<Card> blockCard(@RequestBody CardDto cardDto) {
        logger.info("Блокировка карты с номером: " + cardDto.getCardNumber());
        return ResponseEntity.ok().body(service.blockCard(cardDto.getCardNumber()));
    }

    // Активировать карту пользователя
    @PutMapping("/unblock_card")
    public ResponseEntity<Card> unblockCard(@RequestBody CardDto cardDto) {
        logger.info("Активация карты с номером: " + cardDto.getCardNumber());
        return ResponseEntity.ok().body(service.unblockCard(cardDto.getCardNumber()));
    }

    // Удалить карту пользователя
    @DeleteMapping("/delete_card")
    public ResponseEntity<?> deleteCard(@RequestBody CardDto cardDto) {
        logger.info("Удаление карты с номером: " + cardDto.getCardNumber());
        service.deleteCard(cardDto.getCardNumber());
        return ResponseEntity.ok().build();
    }

    // Выдать все карты
    @GetMapping("/get_all_cards")
    public ResponseEntity<List<Card>> getAllCards() {
        logger.info("Запрос списка карт" );
        return ResponseEntity.ok().body(service.getAllCards());
    }
}
