package com.example.bankcards.controller;

import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.service.serviceimpl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationService service;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    // Авторизация пользователя и администратора
    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        logger.info("Попытка авторизации");
        return ResponseEntity.ok(service.authenticate(request));
    }

}
