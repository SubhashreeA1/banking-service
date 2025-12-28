package com.bank.banking_service.service;

import com.bank.banking_service.entity.Account;
import com.bank.banking_service.entity.Transaction;
import com.bank.banking_service.entity.TransactionType;
import com.bank.banking_service.exception.AccountNotFoundException;
import com.bank.banking_service.exception.InsufficientBalanceException;
import com.bank.banking_service.repository.AccountRepository;
import com.bank.banking_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Account createAccount(String holderName, double initialBalance) {
        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setHolderName(holderName);
        account.setBalance(initialBalance);
        account.setCreatedAt(LocalDateTime.now());

        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found with id: " + accountId));
    }

    @Override
    public Account deposit(Long accountId, double amount) {
        Account account = getAccountById(accountId);
        account.setBalance(account.getBalance() + amount);

        saveTransaction(accountId, amount, "DEPOSIT");
        return accountRepository.save(account);
    }

    @Override
    public Account withdraw(Long accountId, double amount) {
        Account account = getAccountById(accountId);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        saveTransaction(accountId, amount, "WITHDRAW");
        return accountRepository.save(account);
    }

    private void saveTransaction(Long accountId, double amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.valueOf(type));
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {

        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);

        if (fromAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        saveTransaction(fromAccountId, amount, String.valueOf(TransactionType.TRANSFER_OUT));
        saveTransaction(toAccountId, amount, String.valueOf(TransactionType.TRANSFER_IN));
    }

    @Override
    public List<Transaction> getTransactionHistory(Long accountId) {
        getAccountById(accountId); // validate account exists
        return transactionRepository.findByAccountId(accountId);
    }

}

