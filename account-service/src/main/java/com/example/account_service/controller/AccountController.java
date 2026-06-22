package com.example.account_service.controller;

import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return service.findAll();
    }
}