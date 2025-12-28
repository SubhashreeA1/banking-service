package com.bank.banking_service.service;

import com.bank.banking_service.entity.Account;
import com.bank.banking_service.entity.Transaction;
import com.bank.banking_service.exception.InsufficientBalanceException;
import com.bank.banking_service.repository.AccountRepository;
import com.bank.banking_service.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setHolderName("Subha");
        savedAccount.setBalance(5000);

        when(accountRepository.save(any(Account.class)))
                .thenReturn(savedAccount);

        Account result = accountService.createAccount("Subha", 5000);

        assertNotNull(result);
        assertEquals("Subha", result.getHolderName());
        assertEquals(5000, result.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldDepositAmountSuccessfully() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(3000);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        Account updated = accountService.deposit(1L, 1000);

        assertEquals(4000, updated.getBalance());
        verify(transactionRepository, times(1))
                .save(any(Transaction.class));
    }

    @Test
    void shouldWithdrawAmountSuccessfully() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(5000);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Account updated = accountService.withdraw(1L, 2000.0);

        // then
        assertNotNull(updated);
        assertEquals(3000.0, updated.getBalance());
    }

    @Test
    void shouldThrowExceptionWhenBalanceIsLow() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(
                InsufficientBalanceException.class,
                () -> accountService.withdraw(1L, 2000)
        );

        verify(transactionRepository, never())
                .save(any(Transaction.class));
    }

    @Test
    void shouldTransferFundsSuccessfully() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(5000);

        Account to = new Account();
        to.setId(2L);
        to.setBalance(2000);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(from));
        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(to));

        accountService.transfer(1L, 2L, 1000);

        assertEquals(4000, from.getBalance());
        assertEquals(3000, to.getBalance());

        verify(transactionRepository, times(2))
                .save(any(Transaction.class));
    }


}
