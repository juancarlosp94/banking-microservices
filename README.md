# Banking Microservices

A microservices-based banking application built with Java 17, Spring Boot, PostgreSQL, RabbitMQ, Docker, and Maven.

This project demonstrates customer management, account management, transaction processing, reporting, asynchronous communication, containerization, and automated testing using modern backend development practices.

---

## Architecture

### High-Level Architecture

```text
                    +------------------+
                    |      Client      |
                    | (Postman / UI)   |
                    +--------+---------+
                             |
                             |
                +------------v------------+
                |     Customer Service    |
                |-------------------------|
                | REST API                |
                | Customer Management     |
                | PostgreSQL              |
                +------------+------------+
                             |
                             | CustomerCreatedEvent
                             |
                    +--------v--------+
                    |    RabbitMQ     |
                    | customer.created|
                    +--------+--------+
                             |
                             |
                +------------v------------+
                |      Account Service    |
                |-------------------------|
                | REST API                |
                | Accounts                |
                | Movements               |
                | Reports                 |
                | PostgreSQL              |
                +------------+------------+
                             |
                             |
                  +----------v----------+
                  | Customer Reference  |
                  | (local projection)  |
                  +---------------------+
```

---

## Technology Stack

### Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate
* Maven

### Database

* PostgreSQL

### Messaging

* RabbitMQ

### Containerization

* Docker
* Docker Compose

### Testing

* JUnit 5
* Mockito

---

## Features

### Customer Service

* Create customer
* Update customer
* Delete customer
* List customers
* Customer validation

### Account Service

* Create account
* List accounts
* Validate customer ownership
* Customer synchronization through RabbitMQ

### Movement Service

* Deposits
* Withdrawals
* Balance updates
* Insufficient funds validation

### Reporting

* Account statement report
* Date range filtering
* Customer-based reporting

### Integration

* Event-driven communication
* CustomerCreatedEvent publication
* Customer synchronization across services

---

## Project Structure

```text
banking-microservices
│
├── customer-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   └── event
│
├── account-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   └── event
│
├── docker-compose.yml
└── pom.xml
```

---

## Design Patterns and Principles

### Patterns

* Repository Pattern
* DTO Pattern
* Service Layer Pattern
* Event-Driven Architecture

### SOLID Principles

* Single Responsibility Principle
* Dependency Inversion Principle
* Open/Closed Principle

---

## Running the Project

### Build

```bash
mvn clean package -DskipTests
```

### Start Containers

```bash
docker compose up --build
```

Services:

| Service             | Port  |
| ------------------- | ----- |
| Customer Service    | 8081  |
| Account Service     | 8082  |
| RabbitMQ            | 5672  |
| RabbitMQ Management | 15672 |
| Customer DB         | 5433  |
| Account DB          | 5434  |

---

## API Examples

### Create Customer

```http
POST /api/customers
```

Request:

```json
{
  "name": "Jose Lema",
  "gender": "M",
  "age": 30,
  "identification": "12345",
  "address": "Otavalo sn y principal",
  "phone": "098254785",
  "password": "1234",
  "active": true
}
```

---

### Create Account

```http
POST /api/accounts
```

Request:

```json
{
  "accountNumber": "478758",
  "accountType": "Ahorros",
  "initialBalance": 2000,
  "active": true,
  "customerId": 1
}
```

---

### Create Movement

```http
POST /api/movements
```

Request:

```json
{
  "accountNumber": "478758",
  "amount": -575
}
```

---

### Generate Report

```http
GET /api/reports
```

Example:

```http
GET /api/reports?customerId=1&startDate=2026-01-01&endDate=2026-12-31
```

---

## Event Flow

### Customer Creation

```text
Create Customer
      ↓
CustomerCreatedEvent
      ↓
RabbitMQ
      ↓
Account Service
      ↓
Customer Reference Updated
```

### Withdrawal

```text
Create Movement
      ↓
Validate Account
      ↓
Validate Balance
      ↓
Persist Movement
      ↓
Update Balance
      ↓
Return Response
```

---

## Testing

Run all tests:

```bash
mvn test
```

Run Account Service tests:

```bash
account-service/mvnw test
```

Current test coverage includes:

* Successful withdrawal
* Insufficient funds validation

---

## Future Improvements

* OpenAPI / Swagger documentation
* API Gateway
* Authentication and Authorization (JWT)
* Kafka integration
* Distributed tracing
* Centralized logging
* CI/CD pipeline
* Kubernetes deployment

---

## Learning Objectives

This project was created to demonstrate:

* Microservices architecture
* RESTful API design
* Spring Boot development
* PostgreSQL integration
* RabbitMQ messaging
* Docker containerization
* Unit testing with JUnit and Mockito
* Event-driven communication
* Clean architecture principles

---

