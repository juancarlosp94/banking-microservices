package com.example.customer_service.service;

import com.example.customer_service.config.RabbitConfig;
import com.example.customer_service.dto.CustomerRequest;
import com.example.customer_service.dto.CustomerResponse;
import com.example.customer_service.entity.Customer;
import com.example.customer_service.event.CustomerCreatedEvent;
import com.example.customer_service.repository.CustomerRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public CustomerService(CustomerRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public CustomerResponse create(CustomerRequest request) {
        if (repository.existsByIdentification(request.identification())) {
            throw new RuntimeException("Identification already exists");
        }

        Customer customer = new Customer();
        updateFields(customer, request);

        Customer saved = repository.save(customer);

        rabbitTemplate.convertAndSend(
                RabbitConfig.CUSTOMER_EXCHANGE,
                RabbitConfig.CUSTOMER_CREATED_ROUTING_KEY,
                new CustomerCreatedEvent(
                        saved.getId(),
                        saved.getName(),
                        saved.getActive()
                )
        );

        return toResponse(saved);
    }

    private void updateFields(Customer customer, CustomerRequest request) {
        customer.setName(request.name());
        customer.setGender(request.gender());
        customer.setAge(request.age());
        customer.setIdentification(request.identification());
        customer.setAddress(request.address());
        customer.setPhone(request.phone());
        customer.setPassword(request.password());
        customer.setActive(request.active());
    }

    public CustomerResponse findById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return toResponse(customer);
    }

    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        updateFields(customer, request);

        return toResponse(repository.save(customer));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }

        repository.deleteById(id);
    }

    public List<CustomerResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getGender(),
                customer.getAge(),
                customer.getIdentification(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getActive()
        );
    }
}