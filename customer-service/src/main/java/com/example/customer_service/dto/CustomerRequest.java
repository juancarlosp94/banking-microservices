package com.example.customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CustomerRequest(
        @NotBlank
        String name,
        @NotBlank
        String gender,
        @NotNull @Positive
        Integer age,
        @NotBlank
        String identification,
        @NotBlank
        String address,
        @NotBlank
        String phone,
        @NotBlank
        String password,
        @NotNull
        Boolean active
) {
}
