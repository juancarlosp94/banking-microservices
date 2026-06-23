package com.example.customer_service.event;

public record CustomerCreatedEvent(
        Long id,
        String name,
        Boolean active
) {
}