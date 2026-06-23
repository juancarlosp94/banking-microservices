package com.example.account_service.service;

import com.example.account_service.dto.MovementRequest;
import com.example.account_service.entity.Account;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementService movementService;

    @Test
    void shouldWithdrawMoneySuccessfully() {

        Account account = new Account();
        account.setAccountNumber("478758");
        account.setAvailableBalance(new BigDecimal("2000"));

        when(accountRepository.findByAccountNumber("478758"))
                .thenReturn(Optional.of(account));

        when(movementRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MovementRequest request =
                new MovementRequest("478758", new BigDecimal("-575"));

        var response = movementService.create(request);

        assertEquals(new BigDecimal("1425"), account.getAvailableBalance());
        assertEquals(new BigDecimal("-575"), response.amount());
        assertEquals(new BigDecimal("1425"), response.balanceAfterMovement());


        verify(movementRepository).save(any());
    }

    @Test
    void shouldThrowWhenInsufficientFunds() {

        Account account = new Account();
        account.setAccountNumber("478758");
        account.setAvailableBalance(new BigDecimal("100"));

        when(accountRepository.findByAccountNumber("478758"))
                .thenReturn(Optional.of(account));

        MovementRequest request =
                new MovementRequest("478758", new BigDecimal("-200"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> movementService.create(request)
        );

        assertEquals("Saldo no disponible", ex.getMessage());
        verify(movementRepository, never()).save(any());
    }
}