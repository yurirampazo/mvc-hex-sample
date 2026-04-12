# Customer Registry API - Hexagonal Architecture

A modern Spring Boot REST API application implementing the **Hexagonal Architecture** (Ports & Adapters pattern) for managing customers and their addresses. The application demonstrates clean architecture principles with separation of concerns and external dependencies isolated at the boundaries.

## 📋 Project Overview

The **Customer Registry API** is a robust backend service designed to manage customer records and their associated addresses. It integrates with an external address lookup service (ViaCEP) to automatically fetch address details using ZIP codes. The project showcases best practices in enterprise application architecture using Spring Boot 4.0.1 and Java 21.

**Key Features:**
- Customer CRUD operations (Create, Read, Update, Delete)
- Address management with customer associations
- Integration with ViaCEP external API for address lookup
- Hexagonal/Ports & Adapters architecture
- RESTful API with Swagger/OpenAPI documentation
- Health checks and Prometheus metrics
- PostgreSQL database with Flyway migrations
- Docker support for containerized deployment
- Complete separation of business logic from infrastructure

## 🏗️ Project Architecture

The project follows the **Hexagonal Architecture** pattern, which organizes code into three main layers:

### Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE LAYER                     │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  WEB (HTTP Adapters)  │  PERSISTENCE (DB Adapters)   │   │
│  │  - Controllers        │  - Repositories              │   │
│  │  - DTOs               │  - Entities                  │   │
│  │  - Exception Handlers │  - Adapters                  │   │
│  └──────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────┐   │
│  │         CLIENT (External Service Adapters)           │   │
│  │         - ViaCEP Client                              │   │
│  │         - HTTP Client Configuration                  │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────┬─────────────────────────────────────┬──────────┘
             │                                     │
      PORTS (Interfaces)                   PORTS (Interfaces)
       (IN - Primary)                       (OUT - Secondary)
             │                                     │
┌────────────┴─────────────────────────────────────┴──────────┐
│                  APPLICATION LAYER                          │
│           ┌──────────────────────────────┐                  │
│           │    Use Case Services        │                   │
│           │ - CustomerService           │                   │
│           │ - AddressService            │                   │
│           └──────────────────────────────┘                  │
└────────────┬─────────────────────────────────────┬──────────┘
             │                                     │
┌────────────┴─────────────────────────────────────┴──────────┐
│                    DOMAIN LAYER                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │    Business Models & Logic                           │   │
│  │    - Customer                                        │   │
│  │    - Address                                         │   │
│  │    - Port Interfaces                                 │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### Directory Structure

```
mvc/
├── src/
│   ├── main/
│   │   ├── java/com/model/hex/
│   │   │   ├── MvcApplication.java (Spring Boot entry point)
│   │   │   │
│   │   │   ├── domain/                    (CORE BUSINESS LOGIC - Framework Independent)
│   │   │   │   ├── model/
│   │   │   │   │   ├── Customer.java     (Business entity)
│   │   │   │   │   └── Address.java      (Business entity)
│   │   │   │   │
│   │   │   │   └── ports/                (Interface contracts)
│   │   │   │       ├── in/               (Primary Ports - Use Cases)
│   │   │   │       │   ├── CustomerUseCase.java
│   │   │   │       │   └── AddressUseCase.java
│   │   │   │       └── out/              (Secondary Ports - Dependencies)
│   │   │   │           ├── CustomerRepositoryPort.java
│   │   │   │           ├── AddressRepositoryPort.java
│   │   │   │           └── AddressClientPort.java
│   │   │   │
│   │   │   ├── application/               (ORCHESTRATION - Use Case Implementation)
│   │   │   │   └── service/
│   │   │   │       ├── CustomerService.java  (Implements CustomerUseCase)
│   │   │   │       └── AddressService.java   (Implements AddressUseCase)
│   │   │   │
│   │   │   └── infrastructure/             (ADAPTERS - Framework & External Dependencies)
│   │   │       ├── web/                     (HTTP Adapter - Input/Primary)
│   │   │       │   ├── controller/
│   │   │       │   │   ├── CustomerController.java
│   │   │       │   │   └── AddressController.java
│   │   │       │   ├── dto/                 (Data Transfer Objects)
│   │   │       │   │   ├── CreateCustomerRequestDTO.java
│   │   │       │   │   ├── CustomerResponseDTO.java
│   │   │       │   │   ├── UpdateCustomerRequestDTO.java
│   │   │       │   │   ├── CreateAddressRequestDTO.java
│   │   │       │   │   ├── AddressResponseDTO.java
│   │   │       │   │   ├── UpdateAddressRequestDTO.java
│   │   │       │   │   └── ViaCepResponseDTO.java
│   │   │       │   ├── mapper/              (DTO Mappers)
│   │   │       │   │   ├── CustomerMapper.java
│   │   │       │   │   └── AddressMapper.java
│   │   │       │   └── exceptions/          (Error Handling)
│   │   │       │       ├── ExceptionsHandler.java
│   │   │       │       └── ErrorResponse.java
│   │   │       │
│   │   │       ├── persistence/             (Database Adapter - Output/Secondary)
│   │   │       │   ├── adapter/
│   │   │       │   │   ├── CustomerPersistenceAdapter.java
│   │   │       │   │   └── AddressPersistenceAdapter.java
│   │   │       │   ├── repository/          (JPA Repositories)
│   │   │       │   │   ├── CustomerJpaRepository.java
│   │   │       │   │   └── AddressJpaRepository.java
│   │   │       │   ├── entity/              (JPA Entities)
│   │   │       │   │   ├── CustomerEntity.java
│   │   │       │   │   └── AddressEntity.java
│   │   │       │   └── mapper/              (Entity Mappers)
│   │   │       │       ├── CustomerPersistenceMapper.java
│   │   │       │       └── AddressPersistenceMapper.java
│   │   │       │
│   │   │       └── client/                  (External API Adapter - Output/Secondary)
│   │   │           ├── config/
│   │   │           │   └── RestClientConfig.java
│   │   │           ├── AddressClientAdapter.java
│   │   │           └── ViaCepClient.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml              (Main configuration)
│   │       ├── application-local.yml        (Local development config)
│   │       └── sql/
│   │           └── V1__Initial_schema.sql   (Flyway migration)
│   │
│   └── test/
│       └── java/com/model/hex/              (Unit & Integration Tests)
│
├── docker-compose.yaml                      (PostgreSQL + App + Prometheus)
├── docker-compose-metrics.yaml              (Optional metrics setup)
├── Dockerfile                               (Multi-stage build for app)
├── prometheus/
│   └── prometheus.yml                       (Prometheus configuration)
├── pom.xml                                  (Maven configuration)
└── README.md                                (This file)
```

## 🏛️ Architecture Patterns Explained

### Hexagonal Architecture (Ports & Adapters)

The project implements the **Hexagonal Architecture** pattern, also known as Ports and Adapters, which provides several benefits:

**1. Domain Layer (Core):** 
- Pure business logic isolated from any framework
- Models (`Customer`, `Address`) represent business concepts
- Ports are interfaces defining contracts between layers

**2. Ports (Interfaces):**
- **Primary Ports (Inbound):** Use cases that external actors can invoke (CustomerUseCase, AddressUseCase)
- **Secondary Ports (Outbound):** Dependencies that the domain needs (repositories, external clients)

**3. Application Layer (Orchestration):**
- Implements primary ports using services (CustomerService, AddressService)
- Orchestrates business logic and coordinates between ports
- Technology-agnostic use case implementations

**4. Infrastructure Layer (Adapters):**
- **Input Adapters (Primary):** Controllers translate HTTP to port calls
- **Output Adapters (Secondary):** Database and external API adapters implement port interfaces
- Framework-specific code (Spring, JPA, HTTP clients) remains isolated

**Benefits:**
- ✅ Easy to test (mock dependencies via ports)
- ✅ Technology agnostic domain logic
- ✅ Flexible to change persistence mechanisms
- ✅ Clear dependency flow (always towards the core)
- ✅ Facilitates parallel team development

## 🛠️ Technology Stack

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

## 📦 Prerequisites

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

## 🚀 Getting Started - Local Development

### Option 1: Docker Compose (Recommended - Fastest)

This method starts PostgreSQL and the application together with automatic configuration.

```bash
# Clone the repository (if needed)
cd /yourProject

# Create .env file with database credentials
cat > .env << EOF
POSTGRES_USER=postgres
POSTGRES_PASSWORD=yourSecurePassword123
POSTGRES_DB=customer_registry
DB_HOST=db
DB_PORT=5432
DB_NAME=customer_registry
DB_USERNAME=postgres
DB_PASSWORD=yourSecurePassword123
EOF

# Build and start all services
docker-compose up --build

# Application will be available at: http://localhost:8080
# API documentation at: http://localhost:8080/swagger-ui.html
# Prometheus metrics at: http://localhost:9090
```

### Option 2: Local Development with External PostgreSQL

Best for development when you want to debug with IDE.

```bash
# Start PostgreSQL with Docker
docker run --name postgres-dev \
  -e POSTGRES_PASSWORD=yourSecurePassword123 \
  -e POSTGRES_DB=customer_registry \
  -e POSTGRES_USER=postgres \
  -p 5433:5432 \
  -d postgres:16-alpine

# Navigate to project
cd /yourProject

# Create local configuration
cat > src/main/resources/application-local.yml << EOF
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/customer_registry
    username: postgres
    password: yourSecurePassword123
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

via-cep:
  base-url: https://viacep.com.br

server:
  port: 8080
EOF

# Build the project
./mvnw clean package

# Run the application
java -jar target/mvc-1.0.0.jar

# Or run with Maven directly
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
```

### Option 3: Full Manual Setup

For complete control over the environment:

```bash
# 1. Create PostgreSQL database manually
psql -U postgres -c "CREATE DATABASE customer_registry;"

# 2. Navigate to project
cd /yourProject

# 3. Update application.yml with your database credentials
nano src/main/resources/application.yml

# 4. Build the project
./mvnw clean install

# 5. Run the application
./mvnw spring-boot:run
```

## 🧪 Testing the Application

### 1. Health Check

Verify the application is running:

```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "PostgreSQL",
        "hello": 1
      }
    }
  }
}
```

### 2. Create a Customer

```bash
curl -X POST http://localhost:8080/customer \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@example.com",
    "birthDate": "1990-05-15"
  }'
```

Expected response (201 Created):
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@example.com",
  "birthDate": "1990-05-15",
  "addresses": []
}
```

### 3. Create an Address

```bash
curl -X POST http://localhost:8080/address \
  -H "Content-Type: application/json" \
  -d '{
    "zipCode": "01310-100",
    "number": "123",
    "streetName": "Avenida Paulista",
    "neighbourhood": "Centro",
    "city": "São Paulo",
    "state": "SP"
  }'
```

### 4. Fetch Address from ViaCEP (Automatic)

```bash
curl -X GET "http://localhost:8080/address/search/01310-100"
```

This automatically fetches from ViaCEP and returns:
```json
{
  "zipCode": "01310-100",
  "streetName": "Avenida Paulista",
  "neighbourhood": "Centro",
  "city": "São Paulo",
  "state": "SP"
}
```

### 5. Get All Customers

```bash
curl http://localhost:8080/customer
```

### 6. Update a Customer

```bash
curl -X PUT http://localhost:8080/customer/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Updated",
    "email": "joao.updated@example.com",
    "birthDate": "1990-05-15"
  }'
```

### 7. Delete a Customer

```bash
curl -X DELETE http://localhost:8080/customer/1
```

## 📊 Monitoring & Metrics

### Swagger UI / OpenAPI Documentation

Access the interactive API documentation:

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

## 🔧 Building & Packaging

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

## 📝 Database Schema

### Entity Relationships

```
CUSTOMER (1) ─── (M) CUSTOMER_ADDRESS (M) ───(1) ADDRESS
  ├─ id (PK)                                      ├─ id (PK)
  ├─ name                                         ├─ zip_code
  ├─ email (UNIQUE)                              ├─ street_name
  └─ birth_date                                   ├─ neighbourhood
                                                   ├─ city
                                                   └─ state
```

### Flyway Migrations

Database schema is managed using Flyway. Migrations are located in:

```
src/main/resources/sql/
└── V1__Initial_schema.sql
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

## 🐛 Troubleshooting

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

## 📚 API Endpoints

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
| GET | `/address` | List all addresses | - | Array of AddressResponseDTO |
| GET | `/address/{id}` | Get address by ID | - | AddressResponseDTO |
| GET | `/address/search/{zipCode}` | Search address by ZIP code | - | AddressResponseDTO |
| POST | `/address` | Create new address | CreateAddressRequestDTO | AddressResponseDTO (201) |
| PUT | `/address/{id}` | Update address | UpdateAddressRequestDTO | No content (204) |
| DELETE | `/address/{id}` | Delete address | - | No content (204) |

### Management Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/health?showDetails=true` | Detailed health information |
| `/actuator/prometheus` | Prometheus metrics |
| `/swagger-ui.html` | Interactive API documentation |
| `/v3/api-docs` | OpenAPI specification (JSON) |

## 🧩 Key Components Explained

### Services (Application Layer)

**CustomerService** - Implements CustomerUseCase port
- Handles all customer business logic
- Coordinates with repositories and other services
- Transactional operations

**AddressService** - Implements AddressUseCase port
- Manages address operations
- Integrates with ViaCEP for address lookup
- Validates address data

### Adapters (Infrastructure Layer)

**Controllers** (Input Adapters)
- `CustomerController` - Exposes customer endpoints
- `AddressController` - Exposes address endpoints
- Translate HTTP requests to domain operations

**Persistence Adapters** (Output Adapters)
- `CustomerPersistenceAdapter` - Implements CustomerRepositoryPort
- `AddressPersistenceAdapter` - Implements AddressRepositoryPort
- Handle database operations via JPA repositories

**Client Adapter** (Output Adapter)
- `AddressClientAdapter` - Implements AddressClientPort
- Communicates with external ViaCEP API
- Handles HTTP client configuration

### Ports (Contracts)

**Primary Ports (Inbound)**
- `CustomerUseCase` - What external actors can do with customers
- `AddressUseCase` - What external actors can do with addresses

**Secondary Ports (Outbound)**
- `CustomerRepositoryPort` - How to persist customers
- `AddressRepositoryPort` - How to persist addresses
- `AddressClientPort` - How to lookup external addresses

## 🤝 Contributing

When extending the application:

1. **Keep business logic in Domain** - Only pure logic, no Spring annotations
2. **Implement ports as interfaces** - Don't depend on implementations
3. **Use adapters for external integration** - Keep framework code isolated
4. **Map DTOs at boundaries** - Convert between external and internal models
5. **Test through ports** - Mock port implementations in tests

## 📖 Learning Resources

- **Hexagonal Architecture**: https://alistair.cockburn.us/hexagonal-architecture/
- **Domain-Driven Design**: Eric Evans - "Domain-Driven Design" book
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Ports and Adapters**: https://en.wikipedia.org/wiki/Hexagonal_architecture

## 📄 License

This project is provided as-is for educational purposes.

## 📞 Support & Issues

For issues or questions:

1. Check existing GitHub Issues
2. Review logs: `docker logs spring-api`
3. Check database connectivity: `docker exec postgres psql -U postgres -c "\dt"`
4. Enable debug logging in `application.yml`:

```yaml
logging:
  level:
    root: DEBUG
    com.model.hex: DEBUG
```

---

**Last Updated:** April 2026  
**Version:** 1.0.0  
**Architecture:** Hexagonal (Ports & Adapters)
