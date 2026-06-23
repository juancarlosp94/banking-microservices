package com.example.account_service.repository;

import com.example.account_service.entity.CustomerReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReferenceRepository extends JpaRepository<CustomerReference, Long> {
}