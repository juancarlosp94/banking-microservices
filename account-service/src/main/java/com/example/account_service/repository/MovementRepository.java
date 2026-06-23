package com.example.account_service.repository;

import com.example.account_service.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository
        extends JpaRepository<Movement, Long> {
}