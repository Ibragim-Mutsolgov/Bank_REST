package com.example.bankcards.service;

import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.dto.RegisterRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;

import java.util.List;

public interface AdminService {

    AuthenticationResponseDto register(RegisterRequestDto request);

    List<User> getAllUsers();

    void deleteAllUsers();

    User getUserByUsername(String username);

    void deleteUserByUsername(String username);

    List<Card> getAllCards();

    Card createCard(String username);

    Card blockCard(String cardNumber);

    Card unblockCard(String cardNumber);

    void deleteCard(String cardNumber);
}
