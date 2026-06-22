package com.example.account_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private String accountType;

    private BigDecimal initialBalance;

    private BigDecimal availableBalance;

    private Boolean active;

    private Long customerId;

    public Long getId() {
        return id;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountType() {
        return accountType;
    }
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }
    public Boolean getActive() {
        return active;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}