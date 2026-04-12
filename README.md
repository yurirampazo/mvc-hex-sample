
# MVC model: 
## https://github.com/yurirampazo/mvc-sample/tree/feature/mvc

# Hexagonal model: Look on feature/hexagonal branch

## https://github.com/yurirampazo/mvc-sample/tree/feature/hexagonal

# Customer Registry API - Hexagonal Architecture

A modern Spring Boot REST API application implementing the **Hexagonal Architecture** (Ports & Adapters pattern) for managing customers and their addresses. The application demonstrates clean architecture principles with separation of concerns and external dependencies isolated at the boundaries.

## ? Project Overview

The **Customer Registry API** is a robust backend service designed to manage customer records and their associated addresses. It integrates with an external address lookup service (ViaCEP) to automatically fetch address details using ZIP codes. The project showcases best practices in enterprise application architecture using Spring Boot 4.0.1 and Java 21.

**Key Features:**
- Customer CRUD operations (Create, Read, Update, Delete)
- Address management with customer associations
- Integration with ViaCEP external API for address lookup
- RESTful API with Swagger/OpenAPI documentation
- Health checks and Prometheus metrics
- PostgreSQL database with Flyway migrations
- Docker support for containerized deployment


## Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 21 | Programming language |
| **Spring Boot** | 4.0.1 | Framework & runtime |
| **Spring Data JPA** | Latest | ORM & persistence |
| **PostgreSQL** | 16 | Primary database |
| **MySQL** | Latest | Alternative DB driver |
| **Flyway** | Latest | Database migrations |
| **Lombok** | 1.18.42 | Reduce boilerplate code |
| **MapStruct** | 1.6.3 | Type-safe object mapping |
| **Swagger/OpenAPI** | 2.8.5 | API documentation |
| **Prometheus** | Latest | Metrics & monitoring |
| **Micrometer** | 1.16.1 | Metrics collection |
| **Maven** | 3.9.9 | Build tool |
| **Docker** | Latest | Containerization |

## ? Prerequisites

Before running the project, ensure you have the following installed:

- **Java 21+** - [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- **Maven 3.9+** - Included as wrapper (mvnw)
- **PostgreSQL 16** - [Download](https://www.postgresql.org/download/) or use Docker
- **Docker & Docker Compose** - [Download](https://www.docker.com/)
- **Git** - For version control

### Version Verification

```bash
java -version          # Verify Java 21+
mvn --version         # Verify Maven
docker --version      # Verify Docker
docker-compose --version  # Verify Docker Compose
```

## ? Getting Started - Local Development

### Option 1: Docker Compose (Recommended - Fastest)

This method starts PostgreSQL and the application together with automatic configuration.

### Option 2: Local Development with External PostgreSQL

Best for development when you want to debug with IDE.

### API contract:

```
http://localhost:8080/swagger-ui.html
```

Here you can:
- View all available endpoints
- See request/response schemas
- Test endpoints directly from the browser
- Download OpenAPI specification

### Health Endpoint

```bash
curl http://localhost:8080/actuator/health

# For detailed health info
curl http://localhost:8080/actuator/health?showDetails=true
```

### Prometheus Metrics

Access metrics for monitoring and alerting:

```
http://localhost:9090
```

Available metrics:
- `http_server_requests_seconds` - HTTP request metrics
- `jvm_memory_used_bytes` - JVM memory usage
- `db_connection_active` - Database connection pool
- `process_uptime_seconds` - Application uptime

Query examples in Prometheus:
```
rate(http_server_requests_seconds_count[5m])  # Request rate
jvm_memory_used_bytes{area="heap"}             # Heap memory usage
```

## ? Building & Packaging

### Build the Project

```bash
# Clean build with tests
./mvnw clean package

# Build without running tests (faster)
./mvnw clean package -DskipTests

# Build specific module
./mvnw clean install -DskipTests
```

### Build Docker Image

```bash
# Build with Docker Compose
docker-compose build

# Or build standalone
docker build -t mvc-app:1.0.0 .
```

### Generated Artifacts

After building, find in `target/` directory:
- `mvc-1.0.0.jar` - Executable Spring Boot JAR
- `classes/` - Compiled classes
- `generated-sources/` - MapStruct generated mappers

## ? Database Schema

### Entity Relationships

```
CUSTOMER (1) ??? (M) CUSTOMER_ADDRESS (M) ???(1) ADDRESS
  ?? id (PK)                                      ?? id (PK)
  ?? name                                         ?? zip_code
  ?? email (UNIQUE)                              ?? street_name
  ?? birth_date                                   ?? neighbourhood
                                                   ?? city
                                                   ?? state
```

### Flyway Migrations

Database schema is managed using Flyway. Migrations are located in:

```
src/main/resources/sql/
??? V1__Initial_schema.sql
```

**Key Tables:**

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `customer` | Stores customer information | id, name, email, birth_date |
| `address` | Stores address information | id, zip_code, street_name, neighbourhood, city, state |
| `customer_address` | Maps customers to addresses | customer_id, address_id |

### Running Migrations Manually

```bash
# Migrations run automatically on startup
# To reset and re-run: set ddl-auto: create-drop in application.yml
spring.jpa.hibernate.ddl-auto=create-drop
```

## ? Troubleshooting

### Port Already in Use

If ports 8080 (app), 5433 (PostgreSQL), or 9090 (Prometheus) are already in use:

```bash
# Change Docker Compose ports in docker-compose.yaml
# For example, change "8080:8080" to "8081:8080"
# Then access at http://localhost:8081
```

### Database Connection Issues

```bash
# Check if PostgreSQL container is running
docker ps | grep postgres

# View PostgreSQL logs
docker logs postgres

# Manually test PostgreSQL connection
docker exec -it postgres psql -U postgres -d customer_registry
```

### Application Logs

```bash
# View Docker container logs
docker logs spring-api

# View logs with tail (last 100 lines)
docker logs --tail 100 spring-api

# Follow logs in real-time
docker logs -f spring-api
```

### Memory Issues

If experiencing OutOfMemory errors with Docker:

```bash
# Increase Docker memory allocation in docker-compose.yaml
environment:
  JAVA_OPTS: "-Xmx512m -Xms256m"
```

### Rebuild Everything

```bash
# Complete cleanup and rebuild
docker-compose down -v  # Remove volumes
docker-compose build --no-cache
docker-compose up
```

## ? API Endpoints

### Customer Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/customer` | List all customers | - | Array of CustomerResponseDTO |
| GET | `/customer/{id}` | Get customer by ID | - | CustomerResponseDTO |
| POST | `/customer` | Create new customer | CreateCustomerRequestDTO | CustomerResponseDTO (201) |
| PUT | `/customer/{id}` | Update customer | UpdateCustomerRequestDTO | No content (204) |
| DELETE | `/customer/{id}` | Delete customer | - | No content (204) |

### Address Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/address/{id}` | Get address by ID | - | AddressResponseDTO |
| GET | `/address/search/{zipCode}` | Search address by ZIP code | - | AddressResponseDTO |
