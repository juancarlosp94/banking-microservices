package com.example.account_service.event;

public record CustomerCreatedEvent(
        Long id,
        String name,
        Boolean active
) {
}