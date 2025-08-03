package com.example.bankcards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDto {

    private String token;

    private String username;

    private Long cardNumber;
}
