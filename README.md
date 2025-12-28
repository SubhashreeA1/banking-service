# Banking Account Management Service

A Spring Boot RESTful backend application that simulates core banking operations such as
account creation, balance management, fund transfer, and transaction history.

## 🚀 Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- RESTful APIs
- Maven
- JUnit & Mockito
- Git

## 🧩 Features
- Create bank accounts
- Fetch account details
- Deposit & withdraw money
- Fund transfer between accounts
- Transaction history tracking
- Global exception handling
- Input validation
- Unit tested business logic

## 🏗 Architecture
- Layered Architecture (Controller → Service → Repository)
- DTO-based request validation
- Transaction management using @Transactional

## 🔗 API Endpoints
| Method | Endpoint | Description |
|------|---------|-------------|
| POST | /api/accounts | Create account |
| GET | /api/accounts/{id} | Get account |
| POST | /api/accounts/deposit | Deposit funds |
| POST | /api/accounts/withdraw | Withdraw funds |
| POST | /api/accounts/transfer | Transfer funds |
| GET | /api/accounts/{id}/transactions | Transaction history |

## 🧪 Testing
- Unit tests written for service layer
- Mockito used to mock repository interactions

## ▶️ How to Run
1. Clone the repository
2. Update PostgreSQL credentials in `application.properties`
3. Run the Spring Boot application
4. Test APIs using Postman
