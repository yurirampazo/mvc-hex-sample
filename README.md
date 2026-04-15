# mvc-hex-sample (PT-BR)

### Comparação estrutural e de performance entre arquitetura em camadas tradicional (Layered) e arquitetura hexagonal em microsserviços.

Este repositório é um estudo moderno de uma aplicação Spring Boot REST API que compara a **arquitetura MVC em camadas tradicional** com a **Arquitetura Hexagonal** em aplicações web Java. O foco principal está nas camadas de backend, comparando o uso de memória, CPU e latência nos endpoints da aplicação.

Utiliza ferramentas de telemetria como **Grafana**, **Spring Boot Actuator** e **Prometheus** para coletar e visualizar as principais diferenças entre as duas abordagens. Este trabalho faz parte do meu MBA de pós-graduação na instituição **MBA USP/Esalq**.

A **Customer Registry API** é um serviço backend robusto projetado para gerenciar cadastros de clientes e seus endereços associados. Ela integra com um serviço externo de consulta de endereços (**ViaCEP**) para buscar automaticamente os detalhes do endereço a partir do CEP.


Veja mais nas branches que contém cada uma das implementações.

### Modelo MVC (Layered):
https://github.com/yurirampazo/mvc-hex-sample/edit/feature/mvc/README.md

### Modelo Hexagonal:
https://github.com/yurirampazo/mvc-hex-sample/edit/feature/hexagonal/README.md

---

#### Resumo do Projeto

**Principais Funcionalidades:**
- Operações CRUD de clientes (Criar, Ler, Atualizar, Deletar)
- Gerenciamento de endereços com associação a clientes
- Integração com a API externa ViaCEP para consulta de endereços
- API RESTful com documentação via Swagger/OpenAPI
- Health checks e métricas Prometheus
- Banco de dados PostgreSQL com migrações Flyway
- Suporte a Docker para implantação em contêineres

## Stack Tecnológica

| Tecnologia       | Versão      | Propósito                          |
|------------------|-------------|------------------------------------|
| **Java**         | 21          | Linguagem de programação           |
| **Spring Boot**  | 4.0.1       | Framework e runtime                |
| **Spring Data JPA** | Latest   | ORM e persistência                 |
| **PostgreSQL**   | 16          | Banco de dados principal           |
| **MySQL**        | 9.5.0       | Driver alternativo                 |
| **Lombok**       | 1.18.42     | Reduzir código boilerplate         |
| **MapStruct**    | 1.6.3       | Mapeamento type-safe de objetos    |
| **Swagger/OpenAPI** | 2.8.5    | Documentação da API                |
| **Prometheus**   | 1.16.1      | Métricas e monitoramento           |
| **Micrometer**   | 1.16.1      | Coleta de métricas                 |
| **Maven**        | 3.9.9       | Ferramenta de build                |
| **Docker**       | Latest      | Containerização                    |

## Pré-requisitos

- **Java 21+**
- **Maven 3.9+**
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Git**

### Verificação de Versões

```bash
java -version          # Verificar Java 21+
mvn --version          # Verificar Maven
docker --version       # Verificar Docker
docker-compose --version # Verificar Docker Compose
```

# mvc-hex-sample (EN)

### Structural and performance comparison between traditional layered architecture and hexagonal architecture in microservices. 

This repository is a modern study of Spring Boot REST API application comparing layered MVC architecture and Hexagonal Architecture in Java Web Application. It is mainly focused on the backend layers, comparing memory and CPU usage, and latency over the endpoints of the application. Using telemetry tools, Grafana, Spring Boot Actuator and Prometheus to scrap and view the main differences of each one of the approaches. This is a study for my post-graduation MBA of the institution MBA USP/Esalq. 

The **Customer Registry API** is a robust backend service designed to manage customer records and their associated addresses. It integrates with an external address lookup service (ViaCEP) to automatically fetch address details using ZIP codes.

See more in the branches containing each one of the implementation.

### Model MVC (Layered):
https://github.com/yurirampazo/mvc-hex-sample/edit/feature/mvc/README.md

### Model Hexagonal:
https://github.com/yurirampazo/mvc-hex-sample/edit/feature/hexagonal/README.md

#### Project Overview

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
| **MySQL** | 9.5.0 | Alternative DB driver |
| **Lombok** | 1.18.42 | Reduce boilerplate code |
| **MapStruct** | 1.6.3 | Type-safe object mapping |
| **Swagger/OpenAPI** | 2.8.5 | API documentation |
| **Prometheus** | 1.16.1 | Metrics & monitoring |
| **Micrometer** | 1.16.1 | Metrics collection |
| **Maven** | 3.9.9 | Build tool |
| **Docker** | Latest | Containerization |

##  Prerequisites

- **Java 21+** 
- **Maven 3.9+** 
- **PostgreSQL 16**
- **Docker & Docker Compose**
- **Git**

### Version Verification

```bash
java -version          # Verify Java 21+
mvn --version         # Verify Maven
docker --version      # Verify Docker
docker-compose --version  # Verify Docker Compose
```

#### API Endpoints

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
