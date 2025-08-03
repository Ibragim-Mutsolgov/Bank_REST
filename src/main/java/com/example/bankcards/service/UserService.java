package com.example.bankcards.service;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.entity.Card;

import java.util.List;

public interface UserService {

    List<Card> getAllCards(String username);

    CardResponseDto render(String cardNumberSender, String cardNumberRecipient, double sum);

    CardResponseDto block(String cardNumber);

    CardResponseDto getBalance(String cardNumber);
}
