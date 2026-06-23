package com.example.account_service.repository;

import com.example.account_service.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovementRepository
        extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountCustomerIdAndDateBetween(
            Long customerId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}