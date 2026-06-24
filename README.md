# Banking Microservices

A microservices-based banking application built with Java 17, Spring Boot 3, PostgreSQL, RabbitMQ, Docker, and Maven.

This project demonstrates customer management, account management, transaction processing, reporting, asynchronous communication, containerization, validation, exception handling, API documentation, and automated testing using modern backend development practices.

---

# Architecture

## High-Level Architecture

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

# Technology Stack

## Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate
* Maven

## Database

* PostgreSQL

## Messaging

* RabbitMQ

## Containerization

* Docker
* Docker Compose

## Documentation

* Swagger / OpenAPI

## Testing

* JUnit 5
* Mockito

---

# Features

## Customer Service

* Create customer
* Update customer
* Delete customer
* Get customer by ID
* List customers
* Customer validation
* Global exception handling

## Account Service

* Create account
* Get account by ID
* Delete account
* List accounts
* Customer ownership validation



* Customer synchronization through RabbitMQ
* Resource not found handling

## Movement Service

* Deposits
* Withdrawals
* Balance updates
* Insufficient funds validation
* Movement validation

## Reporting

* Account statement report
* Date range filtering
* Customer-based reporting

## Integration

* Event-driven communication
* CustomerCreatedEvent publication
* Customer synchronization across services

---

# Key Architectural Decisions

## Microservices Separation

The application is split into two independent services:

* Customer Service: customer lifecycle management.
* Account Service: accounts, transactions, and reporting.

This separation follows bounded-context principles and allows independent evolution of each domain.

## Event-Driven Synchronization

Customer information is synchronized through RabbitMQ events instead of direct database access between services.

This approach reduces coupling and improves scalability.

## Database per Service

Each microservice owns its own PostgreSQL database.

Services never share database tables directly, preserving autonomy and reducing cross-service dependencies.

## Validation and Error Handling

Input validation is implemented using Jakarta Validation annotations.

Global exception handlers provide consistent API error responses and proper HTTP status codes.

## API Documentation

Swagger/OpenAPI documentation is automatically generated and available for all REST endpoints.

---

# Project Structure

```text
banking-microservices
│
├── customer-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── event
│   └── exception
│
├── account-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── event
│   └── exception
│
├── docker-compose.yml
└── pom.xml
```

---

# Design Patterns and Principles

## Patterns

* Repository Pattern
* DTO Pattern
* Service Layer Pattern
* Event-Driven Architecture

## SOLID Principles

* Single Responsibility Principle (SRP)
* Dependency Inversion Principle (DIP)
* Open/Closed Principle (OCP)

---

# Running the Project

## Build

```bash
mvn clean package -DskipTests
```

## Start Containers

```bash
docker compose up --build
```

---

# Services

| Service             | Port  |
| ------------------- | ----- |
| Customer Service    | 8081  |
| Account Service     | 8082  |
| RabbitMQ            | 5672  |
| RabbitMQ Management | 15672 |
| Customer DB         | 5433  |
| Account DB          | 5434  |

---

# API Documentation

## Customer Service

Swagger UI:

http://localhost:8081/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8081/v3/api-docs

## Account Service

Swagger UI:

http://localhost:8082/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8082/v3/api-docs

---

# Postman Collection

A Postman collection is included under:

```text
docs/postman/Banking Microservices.postman_collection.json
```

Use the environment:

```text
docs/postman/Banking Microservices Local.postman_environment.json
```

Base URLs:

```text
Customer Service:
http://localhost:8081

Account Service:
http://localhost:8082
```

---

# API Examples

## Create Customer

POST `/api/customers`

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

## Create Account

POST `/api/accounts`

```json
{
  "accountNumber": "478758",
  "accountType": "Ahorros",
  "initialBalance": 2000,
  "active": true,
  "customerId": 1
}
```

## Create Movement

POST `/api/movements`

```json
{
  "accountNumber": "478758",
  "amount": -575
}
```

## Generate Report

GET `/api/reports`

Example:

```http
GET /api/reports?customerId=1&startDate=2026-01-01&endDate=2026-12-31
```

---

# Event Flow

## Customer Creation

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

## Withdrawal

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

# Testing

## Run All Tests

```bash
mvn test
```

## Run Account Service Tests

```bash
account-service/mvnw test
```

Current test coverage includes:

* Successful withdrawal
* Account lookup
* Validation scenarios
* Resource not found handling
* Insufficient funds validation

---

# Quick Verification

## Create Customer

Expected result:

* Customer created successfully
* CustomerCreatedEvent published to RabbitMQ

## Create Account

Expected result:

* Account linked to synchronized customer reference

## Create Withdrawal

Expected result:

* Balance updated
* Movement persisted

## Insufficient Funds

Create a withdrawal larger than the available balance.

Expected result:

* HTTP 400
* Business validation message returned

## Swagger

Customer Service:

http://localhost:8081/swagger-ui/index.html

Account Service:

http://localhost:8082/swagger-ui/index.html

---

# Future Improvements

* API Gateway
* Authentication and Authorization (JWT)
* Kafka integration
* Distributed tracing
* Centralized logging
* CI/CD pipeline
* Kubernetes deployment
* Integration tests using Testcontainers
* Contract testing between services

---

# Learning Objectives

This project was created to demonstrate:

* Microservices architecture
* RESTful API design
* Spring Boot development
* PostgreSQL integration
* RabbitMQ messaging
* Docker containerization
* OpenAPI documentation
* Unit testing with JUnit and Mockito
* Event-driven communication
* Clean architecture principles
* SOLID design principles
# Banking Microservices

A microservices-based banking application built with Java 17, Spring Boot 3, PostgreSQL, RabbitMQ, Docker, and Maven.

This project demonstrates customer management, account management, transaction processing, reporting, asynchronous communication, containerization, validation, exception handling, API documentation, and automated testing using modern backend development practices.

---

# Architecture

## High-Level Architecture

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

# Technology Stack

## Backend

* Java 17
* Spring Boot 3
* Spring Data JPA
* Hibernate
* Maven

## Database

* PostgreSQL

## Messaging

* RabbitMQ

## Containerization

* Docker
* Docker Compose

## Documentation

* Swagger / OpenAPI

## Testing

* JUnit 5
* Mockito

---

# Features

## Customer Service

* Create customer
* Update customer
* Delete customer
* Get customer by ID
* List customers
* Customer validation
* Global exception handling

## Account Service

* Create account
* Get account by ID
* Delete account
* List accounts
* Customer ownership validation
* Customer synchronization through RabbitMQ
* Resource not found handling

## Movement Service

* Deposits
* Withdrawals
* Balance updates
* Insufficient funds validation
* Movement validation

## Reporting

* Account statement report
* Date range filtering
* Customer-based reporting

## Integration

* Event-driven communication
* CustomerCreatedEvent publication
* Customer synchronization across services

---

# Key Architectural Decisions

## Microservices Separation

The application is split into two independent services:

* Customer Service: customer lifecycle management.
* Account Service: accounts, transactions, and reporting.

This separation follows bounded-context principles and allows independent evolution of each domain.

## Event-Driven Synchronization

Customer information is synchronized through RabbitMQ events instead of direct database access between services.

This approach reduces coupling and improves scalability.

## Database per Service

Each microservice owns its own PostgreSQL database.

Services never share database tables directly, preserving autonomy and reducing cross-service dependencies.

## Validation and Error Handling

Input validation is implemented using Jakarta Validation annotations.

Global exception handlers provide consistent API error responses and proper HTTP status codes.

## API Documentation

Swagger/OpenAPI documentation is automatically generated and available for all REST endpoints.

---

# Project Structure

```text
banking-microservices
│
├── customer-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── event
│   └── exception
│
├── account-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── event
│   └── exception
│
├── docker-compose.yml
└── pom.xml
```

---

# Design Patterns and Principles

## Patterns

* Repository Pattern
* DTO Pattern
* Service Layer Pattern
* Event-Driven Architecture

## SOLID Principles

* Single Responsibility Principle (SRP)
* Dependency Inversion Principle (DIP)
* Open/Closed Principle (OCP)

---

# Running the Project

## Build

```bash
mvn clean package -DskipTests
```

## Start Containers

```bash
docker compose up --build
```

---

# Services

| Service             | Port  |
| ------------------- | ----- |
| Customer Service    | 8081  |
| Account Service     | 8082  |
| RabbitMQ            | 5672  |
| RabbitMQ Management | 15672 |
| Customer DB         | 5433  |
| Account DB          | 5434  |

---

# API Documentation

## Customer Service

Swagger UI:

http://localhost:8081/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8081/v3/api-docs

## Account Service

Swagger UI:

http://localhost:8082/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8082/v3/api-docs

---

# Postman Collection

A Postman collection is included under:

```text
docs/postman/Banking Microservices.postman_collection.json
```

Use the environment:

```text
docs/postman/Banking Microservices Local.postman_environment.json
```

Base URLs:

```text
Customer Service:
http://localhost:8081

Account Service:
http://localhost:8082
```

---

# API Examples

## Create Customer

POST `/api/customers`

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

## Create Account

POST `/api/accounts`

```json
{
  "accountNumber": "478758",
  "accountType": "Ahorros",
  "initialBalance": 2000,
  "active": true,
  "customerId": 1
}
```

## Create Movement

POST `/api/movements`

```json
{
  "accountNumber": "478758",
  "amount": -575
}
```

## Generate Report

GET `/api/reports`

Example:

```http
GET /api/reports?customerId=1&startDate=2026-01-01&endDate=2026-12-31
```

---

# Event Flow

## Customer Creation

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

## Withdrawal

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

# Testing

## Run All Tests

```bash
mvn test
```

## Run Account Service Tests

```bash
account-service/mvnw test
```

Current test coverage includes:

* Successful withdrawal
* Account lookup
* Validation scenarios
* Resource not found handling
* Insufficient funds validation

---

# Quick Verification

## Create Customer

Expected result:

* Customer created successfully
* CustomerCreatedEvent published to RabbitMQ

## Create Account

Expected result:

* Account linked to synchronized customer reference

## Create Withdrawal

Expected result:

* Balance updated
* Movement persisted

## Insufficient Funds

Create a withdrawal larger than the available balance.

Expected result:

* HTTP 400
* Business validation message returned

## Swagger

Customer Service:

http://localhost:8081/swagger-ui/index.html

Account Service:

http://localhost:8082/swagger-ui/index.html

---

# Future Improvements

* API Gateway
* Authentication and Authorization (JWT)
* Kafka integration
* Distributed tracing
* Centralized logging
* CI/CD pipeline
* Kubernetes deployment
* Integration tests using Testcontainers
* Contract testing between services

---

# Learning Objectives

This project was created to demonstrate:

* Microservices architecture
* RESTful API design
* Spring Boot development
* PostgreSQL integration
* RabbitMQ messaging
* Docker containerization
* OpenAPI documentation
* Unit testing with JUnit and Mockito
* Event-driven communication
* Clean architecture principles
* SOLID design principles
