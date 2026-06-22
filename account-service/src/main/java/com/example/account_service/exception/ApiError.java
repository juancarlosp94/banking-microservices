package com.example.account_service.exception;

import java.time.LocalDateTime;

public record ApiError(
        String message,
        LocalDateTime timestamp
) {
}