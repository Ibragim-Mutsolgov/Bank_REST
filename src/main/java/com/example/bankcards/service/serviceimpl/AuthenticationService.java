package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.dto.AuthenticationRequestDto;
import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final JwtService  jwtService;

    private final AuthenticationManager manager;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            var user = repository.findByUsername(request.getUsername()).orElseThrow(() ->
                    new com.example.bankcards.exception.AuthenticationException("Пользователь не найден"));
            var jwtToken = jwtService.generateToken(user);

            logger.info("Вошел в систему: " + user.getUsername());
            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .build();
        } catch (AuthenticationException | NoSuchElementException e) {
            logger.info("Неправильные данные");
            logger.info(e.getMessage());
            throw e;
        }
    }
}
