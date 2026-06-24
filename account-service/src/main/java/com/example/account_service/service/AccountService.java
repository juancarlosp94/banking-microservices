package com.example.account_service.service;

import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.entity.Account;
import com.example.account_service.exception.ResourceNotFoundException;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.CustomerReferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final CustomerReferenceRepository customerReferenceRepository;

    public AccountService(AccountRepository repository, CustomerReferenceRepository customerReferenceRepository) {
        this.repository = repository;
        this.customerReferenceRepository = customerReferenceRepository;
    }

    public AccountResponse create(AccountRequest request) {
        if (repository.existsByAccountNumber(request.accountNumber())) {
            throw new RuntimeException("Account number already exists");
        }

        if (!customerReferenceRepository.existsById(request.customerId())) {
            throw new RuntimeException("Customer reference not found. Create customer first and wait for async event.");
        }

        Account account = new Account();
        account.setAccountNumber(request.accountNumber());
        account.setAccountType(request.accountType());
        account.setInitialBalance(request.initialBalance());
        account.setAvailableBalance(request.initialBalance());
        account.setActive(request.active());
        account.setCustomerId(request.customerId());

        return toResponse(repository.save(account));
    }

    public List<AccountResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getInitialBalance(),
                account.getAvailableBalance(),
                account.getActive(),
                account.getCustomerId()
        );
    }

    public AccountResponse findById(Long id) {
        Account account = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return toResponse(account);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found");
        }

        repository.deleteById(id);
    }
}