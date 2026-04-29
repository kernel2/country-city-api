# Country City API - Architecture and Design Explanation

## 1. Project Overview

This project is a Java 21 + Maven + Spring Boot backend API designed for a technical test.
It exposes country and city endpoints with:

- In-memory data storage (no database)
- Clear layered architecture
- OpenAPI/Swagger documentation
- Validation and standardized error handling
- Pagination for city listing by country

The main goal is to provide a clean, readable, interview-ready codebase that demonstrates good backend fundamentals without unnecessary complexity.

---

## 2. Architecture Style

This project uses a **layered architecture** (also called controller-service-repository architecture).

### Package responsibilities

- `controller`  
  Handles HTTP requests/responses and delegates business logic.

- `service`  
  Contains application logic (validation flow, pagination logic, orchestration).

- `repository`  
  Provides data access methods (in-memory lists + ID generation).

- `model`  
  Internal domain objects (`Country`, `City`).

- `dto`  
  API request/response contracts.

- `mapper`  
  Centralized conversion between internal models and API DTOs.

- `exception`  
  Unified API error handling and error response model.

- `config`  
  OpenAPI/Swagger configuration.

---

## 3. Why DTOs Are Designed This Way

DTOs are intentionally split into **request DTOs** and **response DTOs** to keep API contracts explicit and stable.

## 3.1 Request DTOs (Input)

- `CountryCreateRequest`
- `CityCreateRequest`

Why:

1. Define exactly what clients are allowed to send.
2. Apply validation at the API boundary (`@NotBlank`, `@NotNull`, `@Min`).
3. Avoid exposing internal model objects directly.
4. Keep create payloads clean (for example, IDs are generated server-side, not provided by clients).

## 3.2 Response DTOs (Output)

- `CountryResponse`
- `CitySummaryResponse`
- `CityDetailsResponse`
- `PagedResponse<T>`

Why:

1. Control exactly what the API returns.
2. Return different detail levels depending on endpoint purpose:
   - summary for list endpoints
   - detailed response for single resource endpoints
3. Protect internal model evolution from external API changes.
4. Keep responses frontend-friendly and documented in OpenAPI.

---

## 4. Mapping Strategy

Mapping is implemented manually (no MapStruct), by design.

- `CountryMapper`
- `CityMapper`

Why manual mapping is used here:

1. The project is small and the mapping logic is simple.
2. Manual mapping is explicit, easy to review, and interview-friendly.
3. It avoids adding extra framework complexity for a small scope.
4. Mapping remains centralized in mapper classes (not spread across services/controllers).

The mappers now include dedicated methods for creation flow:

- `CountryCreateRequest -> Country`
- `CityCreateRequest -> City`

This keeps conversion responsibilities in one place.

---

## 5. Request Flow (How a Typical Endpoint Works)

Example flow for `POST /cities`:

1. **Controller** receives HTTP request and validates input DTO.
2. **Service** applies business rule (country must exist).
3. **Mapper** converts request DTO to internal `City` model.
4. **Repository** saves data in-memory and generates ID.
5. **Mapper** converts saved model to response DTO.
6. **Controller** returns `201 Created`.

This flow keeps concerns separated and predictable.

---

## 6. Error Handling Design

Global error handling is centralized using `@RestControllerAdvice`:

- `ResourceNotFoundException -> 404`
- Validation/type errors -> `400`
- Unexpected errors -> `500`

All errors share a consistent JSON structure (`ErrorResponse`) with:

- `timestamp`
- `status`
- `error`
- `message`
- `path`

Why this matters:

1. Consistent client experience
2. Better troubleshooting
3. Cleaner controller code (no repetitive try/catch)

---

## 7. Pagination Design

Pagination is applied to:

- `GET /countries/{countryId}/cities?page=0&size=10`

Response uses `PagedResponse<T>` with:

- `content`
- `page`
- `size`
- `totalElements`
- `totalPages`
- `last`

Why:

1. Standard API pagination contract
2. Easy integration for frontend/client tools
3. Scales better than returning full lists

---

## 8. OpenAPI/Swagger Approach

The project provides:

- Swagger UI endpoint
- OpenAPI docs endpoint
- OpenAPI contract aligned with implementation (`openapi.yaml`)

Why:

1. API is self-documenting for clients.
2. Faster onboarding and testing.
3. Contract is explicit for technical review.

---

## 9. Why In-Memory Repository Is Used

The repository layer uses in-memory data intentionally.

Why:

1. Matches technical test scope.
2. Removes database setup complexity.
3. Keeps focus on API design, architecture, and code quality.
4. Still demonstrates repository/service separation.

It can be replaced later by database repositories without changing API contracts significantly.

---

## 10. SOLID and Clean Code Perspective

This project reasonably applies SOLID for its size:

- **S (Single Responsibility):** each layer/class has clear responsibility.
- **O (Open/Closed):** mostly good for small project; extendable with new endpoints.
- **L / I:** not heavily applicable due to minimal inheritance/interfaces.
- **D (Dependency Inversion):** dependency injection is used; architecture is practical without overengineering.

Overall, the design is intentionally balanced:

- Not too simple
- Not too complex
- Appropriate for a technical interview project

---

## 11. Final Positioning for Client Communication

This backend is built to be:

1. **Readable:** clear package/layer boundaries
2. **Maintainable:** centralized mapping and error handling
3. **Consistent:** DTO contracts + OpenAPI alignment
4. **Practical:** small scope, no unnecessary enterprise complexity
5. **Demonstrative:** strong technical-test readiness

In short, the architecture choices prioritize clarity, correctness, and maintainability while remaining lightweight and realistic for the project scope.
