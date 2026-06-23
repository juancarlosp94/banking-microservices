package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovementResponse(
        Long id,
        LocalDateTime date,
        String movementType,
        BigDecimal amount,
        BigDecimal balanceAfterMovement,
        String accountNumber
) {
}