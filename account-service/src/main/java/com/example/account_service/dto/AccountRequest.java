package com.example.account_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AccountRequest(
        @NotBlank
        String accountNumber,
        @NotBlank
        String accountType,
        @NotNull @Positive
        BigDecimal initialBalance,
        @NotNull
        Boolean active,
        @NotNull
        Long customerId
) {
}