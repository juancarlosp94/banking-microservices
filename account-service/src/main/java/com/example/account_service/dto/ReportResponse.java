package com.example.account_service.dto;

import java.util.List;

public record ReportResponse(
        Long customerId,
        List<ReportMovementResponse> movements
) {
}