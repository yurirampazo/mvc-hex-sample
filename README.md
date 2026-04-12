# Customer Registry API - MVC Architecture

A modern Spring Boot REST API application implementing the **MVC (Model-View-Controller)** architecture for managing customers and their addresses. The application demonstrates a practical, straightforward approach with clear separation between models, business logic, and controllers.

## 📋 Project Overview

The **Customer Registry API** is a backend service designed to manage customer records and their associated addresses. It integrates with an external address lookup service (ViaCEP) to automatically fetch address details using ZIP codes. The project showcases practical enterprise application architecture using Spring Boot 4.0.1 and Java 21.

**Key Features:**
- Customer CRUD operations (Create, Read, Update, Delete)
- Address management with customer associations
- Integration with ViaCEP external API for address lookup
- MVC (Model-View-Controller) architecture
- RESTful API with Swagger/OpenAPI documentation
- Health checks and Prometheus metrics
- PostgreSQL database with Flyway migrations
- Docker support for containerized deployment
- Clear separation of concerns with layered architecture

## 🏗️ Project Architecture

The project follows the **MVC (Model-View-Controller)** pattern, a traditional three-layer architecture that separates concerns:

### Architecture Overview

```
┌──────────────────────────────────────────────────────────────┐
│                      CLIENT REQUEST                           │
│                      (HTTP Layer)                             │
└──────────────────────┬───────────────────────────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │     CONTROLLER LAYER           │
       │  (Request Handling)            │
       │  - CustomerController          │
       │  - AddressController           │
       │  - Exception Handlers          │
       │  - DTOs & Mappers              │
       └───────────────┬────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │    SERVICE LAYER               │
       │  (Business Logic)              │
       │  - CustomerService             │
       │  - AddressService              │
       │  - Business Rules              │
       │  - Orchestration               │
       └───────────────┬────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │     MODEL LAYER                │
       │  (Data & Persistence)          │
       │  - Customer Entity             │
       │  - Address Entity              │
       │  - CustomerRepository          │
       │  - AddressRepository           │
       │  - JPA Entities                │
       └───────────────┬────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │     DATABASE & EXTERNAL        │
       │  - PostgreSQL Database         │
       │  - ViaCEP HTTP Client          │
       └────────────────────────────────┘
```

### Directory Structure

```
mvc/
├── src/
│   ├── main/
│   │   ├── java/com/model/mvc/
│   │   │   ├── MvcApplication.java          (Spring Boot entry point)
│   │   │   │
│   │   │   ├── model/                       (MODEL LAYER - Data Entities)
│   │   │   │   ├── Customer.java           (JPA Entity - represents customer)
│   │   │   │   ├── Address.java            (JPA Entity - represents address)
│   │   │   │   └── dto/                    (Data Transfer Objects)
│   │   │   │       ├── CreateCustomerRequestDTO.java
│   │   │   │       ├── CustomerResponseDTO.java
│   │   │   │       ├── UpdateCustomerRequestDTO.java
│   │   │   │       ├── CreateAddressRequestDTO.java
│   │   │   │       ├── UpdateAddressRequestDTO.java
│   │   │   │       ├── AddressResponseDTO.java
│   │   │   │       └── ViaCepResponseDTO.java
│   │   │   │
│   │   │   ├── controller/                 (VIEW/CONTROLLER LAYER - HTTP Entry Points)
│   │   │   │   ├── CustomerController.java (REST endpoints for customers)
│   │   │   │   └── AddressConnector.java   (REST endpoints for addresses)
│   │   │   │
│   │   │   ├── service/                    (SERVICE LAYER - Business Logic)
│   │   │   │   ├── CustomerService.java    (Customer business operations)
│   │   │   │   └── AddressService.java     (Address business operations)
│   │   │   │
│   │   │   ├── repository/                 (DATA ACCESS LAYER - Persistence)
│   │   │   │   ├── CustomerRepository.java (JPA repository for Customer)
│   │   │   │   └── AddressRepository.java  (JPA repository for Address)
│   │   │   │
│   │   │   ├── mapper/                     (MAPPING LAYER - DTO <-> Entity conversion)
│   │   │   │   ├── CustomerMapper.java     (MapStruct mapper for Customer)
│   │   │   │   └── AddressMapper.java      (MapStruct mapper for Address)
│   │   │   │
│   │   │   ├── client/                     (EXTERNAL CLIENT LAYER - Third-party APIs)
│   │   │   │   ├── ViaCepClient.java       (HTTP client for ViaCEP API)
│   │   │   │   └── ViaCepResponseDTO.java  (ViaCEP response mapping)
│   │   │   │
│   │   │   ├── config/                     (CONFIGURATION LAYER)
│   │   │   │   └── RestClientConfig.java   (HTTP client configuration)
│   │   │   │
│   │   │   └── exceptions/                 (ERROR HANDLING LAYER)
│   │   │       ├── ExceptionsHandler.java  (Global exception handler)
│   │   │       └── ErrorResponse.java      (Error response structure)
│   │   │
│   │   └── resources/
│   │       ├── application.yml              (Main configuration - database, external APIs)
│   │       ├── application-local.yml        (Local development configuration)
│   │       └── sql/
│   │           └── V1__Initial_schema.sql   (Flyway migration - database schema)
│   │
│   └── test/
│       └── java/com/model/mvc/             (Unit & Integration Tests)
│
├── docker-compose.yaml                     (PostgreSQL + Spring App + Prometheus)
├── docker-compose-metrics.yaml             (Optional: Prometheus + Grafana setup)
├── Dockerfile                              (Multi-stage Docker build)
├── prometheus/
│   └── prometheus.yml                      (Prometheus metrics configuration)
├── pom.xml                                 (Maven dependencies & build config)
└── README.md                               (This file)
```

## 🏛️ Architecture Patterns Explained

### MVC (Model-View-Controller) Pattern

The project implements the classic **MVC pattern** with a supporting persistence and client layer, creating a clean **4-layer architecture**:

**1. Controller Layer (View/Presentation):**
- REST Controllers handle HTTP requests (`CustomerController`, `AddressConnector`)
- Receives JSON payloads and converts to DTOs
- Validates input using Jakarta Validation annotations
- Returns JSON responses to clients
- Delegates business logic to services

**2. Service Layer (Business Logic):**
- `CustomerService` - Customer business operations (CRUD, validation)
- `AddressService` - Address business operations and ViaCEP integration
- Handles orchestration between controllers and repositories
- Implements business rules and transaction management
- No Spring web annotations - pure business logic

**3. Model Layer (Data Objects):**
- **Entities**: `Customer`, `Address` (JPA-annotated for persistence)
- **DTOs**: Request/Response Data Transfer Objects for API contracts
- Mappers (MapStruct) convert between entities and DTOs
- Separates persistence models from API contracts

**4. Repository Layer (Data Access):**
- `CustomerRepository`, `AddressRepository` - Spring Data JPA repositories
- Handles all database operations
- Abstracts SQL and query logic
- Provides standard CRUD operations

**5. Supporting Layers:**
- **Client Layer**: `ViaCepClient` for external API integration
- **Config Layer**: Spring configuration for HTTP clients
- **Exception Handling**: Global exception handler for consistent error responses

**Benefits of MVC:**
- ✅ Simple and straightforward - easy to understand
- ✅ Clear separation of concerns
- ✅ Quick development and prototyping
- ✅ Spring Boot conventions reduce boilerplate
- ✅ Suitable for standard CRUD applications
- ✅ Easy onboarding for new developers

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 21 | Programming language |
| Spring Boot | 4.0.1 | Framework & runtime |
| Spring Data JPA | Latest | ORM & persistence |
| PostgreSQL | 16 | Primary database |
| Flyway | Latest | Database migrations |
| Lombok | 1.18.42 | Reduce boilerplate |
| MapStruct | 1.6.3 | Type-safe object mapping |
| Swagger/OpenAPI | 2.8.5 | API documentation |
| Prometheus | Latest | Metrics & monitoring |
| Micrometer | 1.16.1 | Metrics collection |
| Maven | 3.9.9 | Build tool |
| Docker | Latest | Containerization |

## 📦 Prerequisites

Ensure you have installed:

- **Java 21+** - [Download](https://www.oracle.com/java/technologies/downloads/#java21)
- **Docker & Docker Compose** - [Download](https://www.docker.com/)
- **Maven 3.9+** (included as mvnw wrapper)
- **Git** - For version control

### Verify Installation

```bash
java -version
docker --version
docker-compose --version
```

## 🚀 Getting Started - Local Development

### Option 1: Docker Compose (Recommended - Fastest)

**Complete one-command setup with PostgreSQL, app, and Prometheus:**

```bash
cd /your_folder

# Create .env file with credentials
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

# Services will be available at:
# API: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
# Prometheus: http://localhost:9090
```

**To stop:**
```bash
docker-compose down
```

### Option 2: Local Development with IDE Debugging

**Start PostgreSQL in Docker, run app locally:**

```bash
# Start PostgreSQL
docker run --name postgres-dev \
  -e POSTGRES_PASSWORD=yourSecurePassword123 \
  -e POSTGRES_DB=customer_registry \
  -e POSTGRES_USER=postgres \
  -p 5433:5432 \
  -d postgres:16-alpine

# Navigate to project
cd /yourProject

# Build the project
./mvnw clean package -DskipTests

# Run locally (allows IDE debugging)
./mvnw spring-boot:run

# Application available at: http://localhost:8080
```

### Option 3: Manual Setup (Full Control)

```bash
# 1. Install PostgreSQL locally or via Docker
psql -U postgres -c "CREATE DATABASE customer_registry;"

# 2. Update application.yml with your credentials
nano src/main/resources/application.yml

# 3. Build
./mvnw clean install

# 4. Run
./mvnw spring-boot:run
```

## 🧪 Testing the Application

### 1. Health Check

```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{
  "status": "UP",
  "components": {
    "db": {"status": "UP"}
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

Response (201 Created):
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

### 4. Fetch Address from ViaCEP (Auto-lookup)

```bash
curl -X GET "http://localhost:8080/address/search/01310-100"
```

Automatically fetches from ViaCEP API and returns address details.

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

### Swagger UI - Interactive API Documentation

```
http://localhost:8080/swagger-ui.html
```

Features:
- View all endpoints and schemas
- Test endpoints directly
- Download OpenAPI specification

### Prometheus Metrics

```
http://localhost:9090
```

Available metrics:
- `http_server_requests_seconds` - HTTP request latency
- `jvm_memory_used_bytes` - JVM memory usage
- `process_uptime_seconds` - Application uptime

Query examples:
```
rate(http_server_requests_seconds_count[5m])
jvm_memory_used_bytes{area="heap"}
```

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

**Key Tables:**

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `customer` | Customer information | id, name, email, birth_date |
| `address` | Address information | id, zip_code, street_name, city, state |
| `customer_address` | Customer-Address mapping | customer_id, address_id |

**Flyway Migrations:**

Located in: `src/main/resources/sql/V1__Initial_schema.sql`

Migrations run automatically on startup. To reset:
```yaml
spring.jpa.hibernate.ddl-auto: create-drop
```

## 📚 API Endpoints Summary

### Customer Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/customer` | List all customers |
| GET | `/customer/{id}` | Get customer by ID |
| POST | `/customer` | Create new customer |
| PUT | `/customer/{id}` | Update customer |
| DELETE | `/customer/{id}` | Delete customer |

### Address Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/address/{id}` | Get address by ID |
| GET | `/address/search/{zipCode}` | Search by ZIP code |

### Management Endpoints

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health |
| `/actuator/prometheus` | Prometheus metrics |
| `/swagger-ui.html` | API documentation |
| `/v3/api-docs` | OpenAPI spec (JSON) |

## 🔧 Building & Packaging

### Build with Tests

```bash
./mvnw clean package
```

### Build without Tests (Faster)

```bash
./mvnw clean package -DskipTests
```

### Build Docker Image

```bash
docker-compose build
# or
docker build -t mvc-app:1.0.0 .
```

### Generated Artifacts

After building, in `target/` directory:
- `mvc-1.0.0.jar` - Executable Spring Boot JAR
- `classes/` - Compiled classes
- `generated-sources/` - MapStruct-generated mappers

## 🐛 Troubleshooting

### Port Already in Use

```bash
# Change ports in docker-compose.yaml
# Example: "8080:8080" → "8081:8080"
```

### Database Connection Issues

```bash
# Check if PostgreSQL is running
docker ps | grep postgres

# View PostgreSQL logs
docker logs postgres

# Test connection
docker exec -it postgres psql -U postgres -d customer_registry
```

### View Application Logs

```bash
docker logs spring-api          # View logs
docker logs -f spring-api       # Follow logs
docker logs --tail 100 spring-api  # Last 100 lines
```

### Rebuild Everything

```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up
```

## 🧩 Key Components Overview

### Controllers (View/Presentation Layer)

**CustomerController** - REST endpoints for customer management
- `GET /customer` - Retrieve all customers
- `GET /customer/{id}` - Get customer by ID
- `POST /customer` - Create new customer
- `PUT /customer/{id}` - Update customer
- `DELETE /customer/{id}` - Delete customer
- Validates requests using annotations
- Delegates to `CustomerService`

**AddressConnector** - REST endpoints for address management
- `GET /address` - Retrieve all addresses
- `GET /address/{id}` - Get address by ID
- `GET /address/search/{zipCode}` - Search by ZIP code (ViaCEP integration)
- `POST /address` - Create new address
- `PUT /address/{id}` - Update address
- `DELETE /address/{id}` - Delete address
- Delegates to `AddressService`

### Services (Business Logic Layer)

**CustomerService** - Orchestrates customer operations
- CRUD operations for customers
- Validates customer data
- Manages customer-address relationships
- Uses `CustomerRepository` for persistence
- Uses `CustomerMapper` for DTO conversion
- Transactional operations

**AddressService** - Orchestrates address operations
- CRUD operations for addresses
- Integrates with ViaCEP external API (`ViaCepClient`)
- Fetches address details by ZIP code
- Validates address data
- Uses `AddressRepository` for persistence
- Uses `AddressMapper` for DTO conversion

### Models (Data Layer)

**Customer Entity** - JPA entity representing a customer
- Fields: id, name, email, birthDate, addresses
- Relationships: One-to-many with Address (many-to-many through join table)
- Validations: @NotNull, @Email

**Address Entity** - JPA entity representing an address
- Fields: id, zipCode, streetName, neighbourhood, city, state, number
- Relationships: Many-to-one or many-to-many with Customer
- Validations: @NotNull constraints

**DTOs (Data Transfer Objects)**
- Request DTOs: `CreateCustomerRequestDTO`, `UpdateCustomerRequestDTO`, `CreateAddressRequestDTO`, `UpdateAddressRequestDTO`
- Response DTOs: `CustomerResponseDTO`, `AddressResponseDTO`
- External API DTOs: `ViaCepResponseDTO`

### Data Access (Persistence Layer)

**Repositories**
- `CustomerRepository` - Spring Data JPA for Customer queries
- `AddressRepository` - Spring Data JPA for Address queries
- Provides standard CRUD operations automatically

**Mappers**
- `CustomerMapper` - MapStruct mapper (Customer ↔ CustomerDTO)
- `AddressMapper` - MapStruct mapper (Address ↔ AddressDTO)
- Type-safe, compile-time checked mappings

### External Integration

**ViaCepClient** - HTTP client for Brazilian ZIP code lookup
- Calls ViaCEP API to fetch address details
- Returns formatted address information
- Error handling and response parsing

## 📖 Learning Resources

- [MVC Pattern Overview](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [REST API Best Practices](https://restfulapi.net/)
- [MapStruct Documentation](https://mapstruct.org/)

## 📄 License

This project is provided for educational purposes.

---

**Last Updated:** April 2026  
**Version:** 1.0.0  
**Architecture:** MVC (Model-View-Controller)  
**Contact:** For issues or questions, check application logs or enable DEBUG logging

