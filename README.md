# Java Concurrent Backend Service

A Spring Boot REST API demonstrating advanced concurrency patterns, transactional integrity across async operations, and comprehensive testing strategies. Built to showcase expertise in multi-threaded programming and enterprise Java development.

## Project Overview

This project tackles real-world backend challenges that most CRUD applications ignore:
- Thread-safe concurrent request handling with proper resource management
- Transaction management across thread boundaries - a common pitfall in async systems
- Race condition prevention using atomic operations
- Comprehensive testing including concurrency and integration tests


## Technical Stack

- **Java 20** - Latest language features
- **Spring Boot 3.2.x** - Modern enterprise framework
- **PostgreSQL** - Production-grade RDBMS
- **Spring Data JPA** - Object-relational mapping
- **ExecutorService** - Thread pool management
- **JUnit 5 & Mockito** - Comprehensive test coverage
- **Maven** - Dependency management


## Core Functionality

### REST API Endpoints

| Endpoint | Method | Description | Use Case |
|----------|--------|-------------|----------|
| `/actuator/health` | GET | Health check with DB status | Monitoring & readiness probe |
| `/tasks` | POST | Synchronous task creation | Immediate processing with response |
| `/tasks/async` | POST | Asynchronous task creation | Non-blocking background processing |
| `/tasks/fail` | POST | Forced failure for rollback demo | Transaction integrity validation |
| `/tasks` | GET | Retrieve all tasks | Data retrieval |
| `/tasks/query` | GET | Paginated query by status | Scalable data access |
| `/tasks/processed-count` | GET | Get async task counter | Thread-safe counter verification |

**API Features:**
- Request validation with Jakarta Bean Validation
- Proper HTTP status codes (200, 400, 500)
- JSON request/response handling
- Pagination support for large datasets

## Testing

The project includes several types of tests:

- **Unit tests** - Test individual methods with mocks
- **Integration tests** - Test with a real database
- **Concurrency tests** - Make sure thread safety works
- **Controller tests** - Test the REST endpoints


### Running Tests
```bash
mvn test
```

Test files:
- [TaskServiceTest.java](src/test/java/com/backend/service/TaskServiceTest.java)
- [TaskServiceConcurrencyTest.java](src/test/java/com/backend/service/TaskServiceConcurrencyTest.java)
- [TaskRepositoryTest.java](src/test/java/com/backend/repository/TaskRepositoryTest.java)
- [TaskControllerTest.java](src/test/java/com/backend/controller/TaskControllerTest.java)

## Project Architectureoncurrency Testing:**
```java
// Simulates 100 concurrent requests to verify thread safety
@Test
void shouldHandleConcurrentRequests() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(100);
    // Tests AtomicInteger correctness under load
    // Validates no race conditions in counter updates
}
```

**Transaction Rollback Testing:**
```java
// Verifies database consistency on failure
@Test
void shouldRollbackOnException() {
    assertThrows(TaskCreationException.class, () -> 
        taskService.createTaskAndFail(task)
    );
    // Confirms no partial data commits
}
```


## Project Architecture

```
src/main/java/com/backend/
├── config/
│   └── TaskExecutorConfig.java          # Thread pool configuration & lifecycle
├── controller/
│   ├── HealthController.java            # Actuator health endpoints
│   ├── TaskController.java              # Sync/async task operations
│   └── TaskQueryController.java         # Paginated queries
├── exception/
│   ├── GlobalExceptionHandler.java      # Centralized error handling
│   └── TaskCreationException.java       # Custom business exception
├── model/
│   └── Task.java                        # JPA entity with validation
├── repository/
│   └── TaskRepository.java              # Spring Data JPA interface
├── service/
│   ├── TaskService.java                 # Core business logic + async
│   └── TaskQueryService.java            # Query operations
└── ConcurrentServiceApplication.java    # Spring Boot entry point

src/test/java/com/backend/
├── controller/                          # HTTP layer tests
├── repository/                          # Data access tests
├── service/                             # Business logic + concurrency tests
├── ApplicationContextTest.java          # Spring context validation
└── IntegrationFlowTest.java            # End-to-end integration tests
```

### Critical Implementation Files

| File | Technical Highlight |
|------|---------------------|
| [TaskService.java](src/main/java/com/backend/service/TaskService.java) | Worker-thread transaction boundaries, async processing |
| [TaskExecutorConfig.java](src/main/java/com/backend/config/TaskExecutorConfig.java) | ExecutorService configuration with graceful shutdown |
| [TaskServiceConcurrencyTest.java](src/test/java/com/backend/service/TaskServiceConcurrencyTest.java) | Race condition testing with CountDownLatch |
| [GlobalExceptionHandler.java](src/main/java/com/backend/exception/GlobalExceptionHandler.java) | @ControllerAdvice for centralized error handling |

## Why This Project Stands Out

- Solves real production problems - most student projects ignore concurrency
- Shows deep technical understanding of framework internals, not just API usage
- Production-ready code quality with proper error handling and validation
- Comprehensive testing including difficult-to-test concurrent behavior
- Performance awareness with database indexing, pagination, and connection pooling


---

**Thumbeti Chaitanya Venkata Sai**  
Computer Science Student

This project demonstrates production-level Java backend development skills including concurrency management, transaction handling, and enterprise-grade testing practices.