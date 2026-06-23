package com.example.account_service.dto;

import java.math.BigDecimal;

public record MovementRequest(
        String accountNumber,
        BigDecimal amount
) {
}