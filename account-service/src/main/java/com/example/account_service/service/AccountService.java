package com.example.account_service.service;

import com.example.account_service.dto.AccountRequest;
import com.example.account_service.dto.AccountResponse;
import com.example.account_service.entity.Account;
import com.example.account_service.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public AccountResponse create(AccountRequest request) {
        if (repository.existsByAccountNumber(request.accountNumber())) {
            throw new RuntimeException("Account number already exists");
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
}