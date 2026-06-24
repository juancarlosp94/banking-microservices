package com.example.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MovementRequest(
        @NotBlank
        String accountNumber,
        @NotNull
        BigDecimal amount
) {
}