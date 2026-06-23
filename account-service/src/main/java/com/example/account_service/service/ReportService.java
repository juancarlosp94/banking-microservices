package com.example.account_service.service;

import com.example.account_service.dto.ReportMovementResponse;
import com.example.account_service.dto.ReportResponse;
import com.example.account_service.entity.Movement;
import com.example.account_service.repository.MovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReportService {

    private final MovementRepository movementRepository;

    public ReportService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public ReportResponse report(Long customerId, LocalDate startDate, LocalDate endDate) {
        List<Movement> movements = movementRepository.findByAccountCustomerIdAndDateBetween(
                customerId,
                startDate.atStartOfDay(),
                endDate.atTime(LocalTime.MAX)
        );

        List<ReportMovementResponse> response = movements.stream()
                .map(movement -> new ReportMovementResponse(
                        movement.getDate(),
                        movement.getAccount().getAccountNumber(),
                        movement.getAccount().getAccountType(),
                        movement.getAccount().getInitialBalance(),
                        movement.getAccount().getActive(),
                        movement.getAmount(),
                        movement.getBalanceAfterMovement()
                ))
                .toList();

        return new ReportResponse(customerId, response);
    }
}