# Java Concurrent Backend Service

A production-style Java backend service demonstrating REST APIs, database integration,
transactional consistency, concurrency handling, and performance-aware design.

This project is built incrementally with verification at each step and follows
industry-standard backend engineering practices.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- ExecutorService (Java Concurrency)
- Maven
- JUnit (upcoming)

---

## Architecture

Layered architecture with clear separation of concerns:

- Controller layer: HTTP request/response handling
- Service layer: business logic, transactions, concurrency control
- Repository layer: database access via JPA
- Database: PostgreSQL with manual schema control

---

## Features Implemented

### REST APIs
- Health check endpoint
- Task creation and retrieval
- Asynchronous task submission

### Database Integration
- PostgreSQL integration with manual schema management
- JPA entity-to-table validation (`ddl-auto=validate`)
- Indexing for query optimization
- Execution plan analysis using `EXPLAIN ANALYZE`

### Transactions & Consistency
- Explicit transaction boundaries using `@Transactional`
- Verified rollback behavior on failure
- Custom exception handling with global error handler
- No partial writes under failure scenarios

### Concurrency & Thread Safety
- Asynchronous processing using `ExecutorService` with a fixed thread pool
- Demonstrated race condition on shared mutable state
- Fixed race condition using `AtomicInteger`
- Verified correctness under high concurrent load
- Clear distinction between database consistency and in-memory thread safety

### Performance & Scalability
- Eliminated unbounded reads (`findAll`)
- Implemented paginated, filtered queries
- Index-backed queries validated via execution plans
- Controlled resource usage under concurrency

---

## API Overview

### Health
GET /health

### Tasks
POST /tasks
GET /tasks
POST /tasks/async
GET /tasks/processed-count


### Paginated Query
GET /tasks/query?status={status}&page={page}&size={size}


---

## Testing & Verification

- All endpoints tested using HTTP clients (VS Code REST, PowerShell)
- Concurrent behavior validated using parallel PowerShell jobs
- Database state verified directly via SQL
- Rollback behavior verified against real PostgreSQL instance

Testing artifacts are maintained as `.http` files for repeatability.

---

## Git & Development Process

- Feature-branch workflow
- Small, focused commits
- Merge only after verification
- No placeholder or untested code merged to main

---

## Current Status

- Core backend functionality complete
- Concurrency correctness established
- Database performance considerations implemented

Upcoming:
- Unit and integration testing with JUnit
- Concurrent test cases
- Further hardening and cleanup

---

## Notes

This project prioritizes correctness, clarity, and explainability over feature volume.
All implemented behavior can be demonstrated and justified during interviews.
