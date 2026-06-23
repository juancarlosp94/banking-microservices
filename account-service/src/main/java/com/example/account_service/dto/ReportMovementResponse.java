package com.example.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReportMovementResponse(
        LocalDateTime date,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        Boolean active,
        BigDecimal movement,
        BigDecimal availableBalance
) {
}