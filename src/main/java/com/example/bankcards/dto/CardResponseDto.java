package com.example.bankcards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

    private String cardNumber;
    private double balance;
    private boolean toBlock;
}
