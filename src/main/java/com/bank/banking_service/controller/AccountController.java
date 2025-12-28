package com.bank.banking_service.controller;

import com.bank.banking_service.dto.CreateAccountRequest;
import com.bank.banking_service.dto.TransactionRequest;
import com.bank.banking_service.entity.Account;
import com.bank.banking_service.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 1️⃣ Create Account
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(
                request.getHolderName(),
                request.getInitialBalance()
        );
    }

    // 2️⃣ Get Account by ID
    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // 3️⃣ Deposit
    @PostMapping("/deposit")
    public Account deposit(@Valid @RequestBody TransactionRequest request) {
        return accountService.deposit(
                request.getAccountId(),
                request.getAmount()
        );
    }

    // 4️⃣ Withdraw
    @PostMapping("/withdraw")
    public Account withdraw(@Valid @RequestBody TransactionRequest request) {
        return accountService.withdraw(
                request.getAccountId(),
                request.getAmount()
        );
    }
}

