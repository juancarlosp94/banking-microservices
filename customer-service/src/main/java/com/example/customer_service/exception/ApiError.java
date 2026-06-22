package com.example.customer_service.exception;

import java.time.LocalDateTime;

public record ApiError(
        String message,
        LocalDateTime timestamp
) {
}