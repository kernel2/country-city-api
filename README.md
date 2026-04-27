# Country City API

## Overview
Country City API is a Spring Boot REST backend that exposes country and city data through clean, paginated endpoints.  
The current implementation uses **in-memory sample data** (no database).

## Tech Stack
- Java 21
- Maven
- Spring Boot 4.0.6
- Spring Web
- Spring Validation
- Spring Boot Actuator
- Springdoc OpenAPI + Swagger UI
- JUnit 5

## Prerequisites
- JDK 21
- Maven 3.9+

## Run the Application
```bash
mvn spring-boot:run
```

## Run Tests
```bash
mvn test
```

## API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui`
- OpenAPI Docs: `http://localhost:8080/api-docs`

## Available Endpoints
- `GET /countries`
- `POST /countries`
- `GET /countries/{countryId}/cities?page=0&size=10`
- `GET /cities/{cityId}`
- `POST /cities`

## Create Country Example
Request:
- `POST /countries`
- Body:
  - `name`: `Portugal`

Success response:
- HTTP `201 Created`
- Body shape:
  - `id`
  - `name`

## Create City Example
Request:
- `POST /cities`
- Body:
  - `name`: `Porto`
  - `countryId`: `5`
  - `population`: `237591`
  - `zipCode`: `4000-001`
  - `description`: `Coastal city in northern Portugal`

Success response:
- HTTP `201 Created`
- Body shape:
  - `id`
  - `name`
  - `countryId`
  - `population`
  - `zipCode`
  - `description`

Validation / error behavior:
- Invalid payload returns HTTP `400`
- Unknown `countryId` on city creation returns HTTP `404`

## Pagination Example
Request:
- `GET /countries/1/cities?page=0&size=2`

Response shape:
- `content`
- `page`
- `size`
- `totalElements`
- `totalPages`
- `last`

## Project Structure Summary
- `controller`: REST endpoints
- `service`: business logic layer
- `repository`: in-memory data access
- `model`: domain models
- `dto`: API response models
- `mapper`: model-to-DTO mapping
- `exception`: error model + global exception handling
- `config`: OpenAPI/Swagger configuration
