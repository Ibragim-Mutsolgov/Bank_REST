package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findCardByCardNumber(String cardNumber);

    void deleteCardByCardNumber(String cardNumber);

    List<Card> findAllCardsByUser(User user);

    void deleteCardByUser(User user);
}
