package com.bank.banking_service.service;

import com.bank.banking_service.entity.Account;
import com.bank.banking_service.entity.Transaction;

import java.util.List;

public interface AccountService {
    Account createAccount(String holderName, double initialBalance);

    Account getAccountById(Long accountId);

    Account deposit(Long accountId, double amount);

    Account withdraw(Long accountId, double amount);

    void transfer(Long fromAccountId, Long toAccountId, double amount);

    List<Transaction> getTransactionHistory(Long accountId);
}
