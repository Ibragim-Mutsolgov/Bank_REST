package com.example.bankcards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String username;
    private String cardNumber;
    private String cardNumberSender;
    private String cardNumberRecipient;
    private double balance;
    private boolean toBlock;
}
