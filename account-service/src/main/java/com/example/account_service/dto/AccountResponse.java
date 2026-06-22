package com.example.account_service.dto;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        BigDecimal availableBalance,
        Boolean active,
        Long customerId
) {
}