# Employee Management System — Backend (Spring Boot)

REST API rebuild of the original console-based Employee Management System.

## Stack
Java 17 · Spring Boot 3.3 · Spring Data JPA · Microsoft SQL Server · Bean Validation · Lombok · springdoc-openapi

## Run locally
1. Create the `ems_db` database on your local SQL Server instance (see SQL script below).
2. Set the `DB_PASSWORD` environment variable (and `DB_USERNAME` if not using `sa`) before starting the app —
   credentials are never hardcoded in `application.properties`.
   ```
   CREATE DATABASE ems_db;
   ```
3. `mvn spring-boot:run`
4. API: `http://localhost:8080/api/employees`
5. Swagger UI: `http://localhost:8080/swagger-ui.html`

## Demo login
`POST /api/auth/login` with `{ "username": "admin", "password": "admin123" }` (see `app.admin.*` in
`application.properties`). This is a demo-level gate for the dashboard UI, not production security.

## Package structure
```
com.ems
├── config       CORS + OpenAPI setup
├── controller   REST endpoints
├── dto          Request/response payloads
├── entity       JPA entities
├── exception    Custom exceptions + global handler
├── mapper       Entity <-> DTO conversion
├── repository   Spring Data JPA repositories
└── service      Business logic (interfaces + impl)
```
