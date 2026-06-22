package com.example.customer_service.dto;

public record CustomerRequest(
        String name,
        String gender,
        Integer age,
        String identification,
        String address,
        String phone,
        String password,
        Boolean active
) {
}