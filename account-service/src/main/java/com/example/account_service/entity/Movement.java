package com.example.account_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String movementType;

    private BigDecimal amount;

    private BigDecimal balanceAfterMovement;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Long getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMovementType() {
        return movementType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfterMovement() {
        return balanceAfterMovement;
    }

    public Account getAccount() {
        return account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBalanceAfterMovement(BigDecimal balanceAfterMovement) {
        this.balanceAfterMovement = balanceAfterMovement;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}