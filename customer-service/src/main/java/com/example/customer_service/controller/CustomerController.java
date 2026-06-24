package com.example.customer_service.controller;

import com.example.customer_service.dto.CustomerRequest;
import com.example.customer_service.dto.CustomerResponse;
import com.example.customer_service.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @Operation(summary = "Create customer", description = "Creates a new customer and publishes a CustomerCreatedEvent")
    @PostMapping
    public CustomerResponse create(@Valid @RequestBody CustomerRequest request) {
        return service.create(request);
    }

    @Operation(summary = "List customers", description = "Returns all registered customers")
    @GetMapping
    public List<CustomerResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get customer by ID", description = "Returns a single customer using the customer ID")
    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Update customer", description = "Updates an existing customer's personal and status information")
    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Long id,
            @RequestBody CustomerRequest request
    ) {
        return service.update(id, request);
    }

    @Operation(summary = "Delete customer", description = "Deletes an existing customer by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}