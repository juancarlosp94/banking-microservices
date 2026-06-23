package com.example.account_service.controller;

import com.example.account_service.dto.MovementRequest;
import com.example.account_service.dto.MovementResponse;
import com.example.account_service.service.MovementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @PostMapping
    public MovementResponse create(@RequestBody MovementRequest request) {
        return service.create(request);
    }
}