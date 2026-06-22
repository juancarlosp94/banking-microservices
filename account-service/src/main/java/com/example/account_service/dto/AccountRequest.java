package com.example.account_service.dto;

import java.math.BigDecimal;

public record AccountRequest(
        String accountNumber,
        String accountType,
        BigDecimal initialBalance,
        Boolean active,
        Long customerId
) {
}