package com.example.account_service.service;

import com.example.account_service.dto.MovementRequest;
import com.example.account_service.dto.MovementResponse;
import com.example.account_service.entity.Account;
import com.example.account_service.entity.Movement;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovementService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    public MovementService(
            AccountRepository accountRepository,
            MovementRepository movementRepository
    ) {
        this.accountRepository = accountRepository;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public MovementResponse create(MovementRequest request) {
        Account account = accountRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal newBalance = account.getAvailableBalance().add(request.amount());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo no disponible");
        }

        account.setAvailableBalance(newBalance);

        Movement movement = new Movement();
        movement.setDate(LocalDateTime.now());
        movement.setAmount(request.amount());
        movement.setBalanceAfterMovement(newBalance);
        movement.setMovementType(request.amount().compareTo(BigDecimal.ZERO) < 0 ? "RETIRO" : "DEPOSITO");
        movement.setAccount(account);

        Movement saved = movementRepository.save(movement);
        accountRepository.save(account);

        return toResponse(saved);
    }

    private MovementResponse toResponse(Movement movement) {
        return new MovementResponse(
                movement.getId(),
                movement.getDate(),
                movement.getMovementType(),
                movement.getAmount(),
                movement.getBalanceAfterMovement(),
                movement.getAccount().getAccountNumber()
        );
    }
}