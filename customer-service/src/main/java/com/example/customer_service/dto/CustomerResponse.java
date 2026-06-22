package com.example.customer_service.dto;

public record CustomerResponse(
        Long id,
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone,
        Boolean active
) {
}