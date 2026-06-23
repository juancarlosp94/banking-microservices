package com.example.account_service.event;

import com.example.account_service.config.RabbitConfig;
import com.example.account_service.entity.CustomerReference;
import com.example.account_service.repository.CustomerReferenceRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CustomerCreatedListener {

    private final CustomerReferenceRepository repository;

    public CustomerCreatedListener(CustomerReferenceRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitConfig.CUSTOMER_CREATED_QUEUE)
    public void handle(CustomerCreatedEvent event) {
        CustomerReference customer = new CustomerReference();
        customer.setId(event.id());
        customer.setName(event.name());
        customer.setActive(event.active());

        repository.save(customer);
    }
}