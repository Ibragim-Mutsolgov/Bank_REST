package com.example.bankcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "CardNumber", nullable = false)
    private String cardNumber;

    @Column(name = "Date", nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private Status status;

    @Column(name = "Balance", nullable = false)
    private double balance;

    @Column(name = "to_block", nullable = false)
    private boolean to_block;
}
