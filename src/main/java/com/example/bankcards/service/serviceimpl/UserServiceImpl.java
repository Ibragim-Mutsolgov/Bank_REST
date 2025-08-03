package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.AuthenticationException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                    new AuthenticationException("Пользователь не найден"));
    }

    @Override
    public List<Card> getAllCards(String username) {
        return cardRepository.findAllCardsByUser(userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationException("Пользователь не найден")));
    }

    @Override
    public CardResponseDto render(String cardNumberSender, String cardNumberRecipient, double sum) {
        Card cardSender = cardRepository.findCardByCardNumber(cardNumberSender);
        Card cardRecipient = cardRepository.findCardByCardNumber(cardNumberRecipient);
        double amount = cardSender.getBalance() + cardRecipient.getBalance();
        cardRecipient.setBalance(amount);
        cardRepository.save(cardRecipient);
        return CardResponseDto.builder()
                .cardNumber(cardNumberRecipient)
                .balance(amount)
                .build();
    }

    @Override
    public CardResponseDto block(String cardNumber) {
        Card card = cardRepository.findCardByCardNumber(cardNumber);
        card.setTo_block(true);
        cardRepository.save(card);
        return CardResponseDto.builder()
                .cardNumber(card.getCardNumber())
                .toBlock(true)
                .build();
    }

    @Override
    public CardResponseDto getBalance(String cardNumber) {
        return CardResponseDto.builder()
                .balance(cardRepository.findCardByCardNumber(cardNumber).getBalance())
                .build();
    }
}
