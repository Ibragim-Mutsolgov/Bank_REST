package com.example.bankcards.service.serviceimpl;

import com.example.bankcards.dto.AuthenticationResponseDto;
import com.example.bankcards.dto.RegisterRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.Status;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.AuthenticationException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.JwtService;
import com.example.bankcards.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDto register(RegisterRequestDto request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByRole(Role.USER);
    }

    @Override
    public void deleteAllUsers() {
        cardRepository.deleteAll();
        userRepository.deleteUsersByRole(Role.USER);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationException("Пользователь не найден"));
    }

    @Override
    public void deleteUserByUsername(String username) {
        cardRepository.deleteCardByUser(userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationException("Пользователь не найден")));
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public List<Card> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setCardNumber(
                    "**** **** **** "
                            + cards.get(i).getCardNumber().substring(11, 15));
        }
        return cards;
    }

    @Override
    public Card createCard(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationException("Пользователь не найден"));
        Card card = new Card();
        card.setBalance(0);
        card.setDate(new Date());
        card.setStatus(Status.ACTIVE);
        card.setCardNumber(generateCardNumber());
        card.setUser(user);
        card.setTo_block(false);
        user.getCard().add(card);
        card = cardRepository.save(card);
        userRepository.save(user);
        return card;
    }

    @Override
    public Card blockCard(String cardNumber) {
        Card card = cardRepository.findCardByCardNumber(cardNumber);
        card.setTo_block(true);
        card.setStatus(Status.BLOCKED);
        cardRepository.save(card);
        return card;
    }

    @Override
    public Card unblockCard(String cardNumber) {
        Card card = cardRepository.findCardByCardNumber(cardNumber);
        card.setTo_block(false);
        card.setStatus(Status.ACTIVE);
        cardRepository.save(card);
        return card;
    }

    @Override
    public void deleteCard(String cardNumber) {
        cardRepository.deleteCardByCardNumber(cardNumber);
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < 15; i++)
            number.append(random.nextInt(10));
        int checkDigit = calculateLuhnCheckDigit(number.toString());
        number.append(checkDigit);
        return number.toString();
    }

    private int calculateLuhnCheckDigit(String cardNumber) {
        int sum = 0;
        boolean alternate = true;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }
}
