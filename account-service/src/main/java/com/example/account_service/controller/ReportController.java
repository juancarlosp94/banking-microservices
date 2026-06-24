package com.example.account_service.controller;

import com.example.account_service.dto.ReportResponse;
import com.example.account_service.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @Operation(summary = "Generate report", description = "Returns account movements by customer and date range")
    @GetMapping
    public ReportResponse report(
            @RequestParam("customerId") Long customerId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate
    ) {
        return service.report(customerId, startDate, endDate);
    }
}