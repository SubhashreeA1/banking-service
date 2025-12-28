package com.bank.banking_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private double amount;

    //private String type; // DEPOSIT, WITHDRAW, TRANSFER
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime timestamp;
}
