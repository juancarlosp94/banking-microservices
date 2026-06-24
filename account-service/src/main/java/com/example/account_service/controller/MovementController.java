package com.example.account_service.controller;

import com.example.account_service.dto.MovementRequest;
import com.example.account_service.dto.MovementResponse;
import com.example.account_service.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @Operation(summary = "Create movement", description = "Creates a deposit or withdrawal and updates the account balance")
    @PostMapping
    public MovementResponse create(@Valid @RequestBody MovementRequest request) {
        return service.create(request);
    }
}