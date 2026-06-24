package com.example.account_service.controller;

import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @Operation(summary = "Create account", description = "Creates a new bank account for an existing customer reference")
    @PostMapping
    public AccountResponse create(@Valid @RequestBody AccountRequest request) {
        return service.create(request);
    }

    @Operation(summary = "List accounts", description = "Returns all bank accounts")
    @GetMapping
    public List<AccountResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get account by ID", description = "Returns a single bank account by ID" )
    @GetMapping("/{id}")
    public AccountResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Delete account", description = "Deletes an existing bank account by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}