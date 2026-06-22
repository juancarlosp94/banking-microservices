package com.example.customer_service.controller;

import com.example.customer_service.dto.CustomerRequest;
import com.example.customer_service.dto.CustomerResponse;
import com.example.customer_service.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerResponse create(@RequestBody CustomerRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return service.findAll();
    }
}