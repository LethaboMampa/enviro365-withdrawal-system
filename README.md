# Enviro365 Withdrawal Management System

## Overview

The **Enviro365 Withdrawal Management System** is a full-stack web application developed as part of the **Enviro365 Junior Java Developer Assessment**.

The application enables investors to securely manage their investment portfolios, submit withdrawal requests, view withdrawal history, update profile information, and generate downloadable withdrawal reports.

The system consists of a **Spring Boot REST API** backend and a **React.js** frontend communicating through RESTful web services.

---

# Features

## Investor Management

* Secure investor login
* View investor profile
* Update personal information
* Read-only email, ID number, and date of birth

---

## Portfolio Management

* View all investor portfolios
* Display portfolio balance
* Display investment products
* Support for multiple portfolio types

Portfolio types:

* Retirement
* Savings
* Investment

---

## Withdrawal Management

Investors can:

* Submit withdrawal requests
* View withdrawal history
* Track withdrawal status

Withdrawal statuses:

* Pending
* Approved
* Rejected

---

## Business Rules

The application validates every withdrawal before it is submitted.

### Rule 1

Retirement withdrawals are only allowed for investors older than **65 years**.

### Rule 2

Withdrawal amount cannot exceed the available portfolio balance.

### Rule 3

Withdrawal amount cannot exceed **90%** of the available portfolio balance.

If any rule is violated, a meaningful error message is returned.

---

## Reporting

The system allows investors to generate reports using filters.

Supported filters:

* Date From
* Date To
* Withdrawal Status
* Amount
* Amount comparison (Greater Than, Less Than, Equal)

Reports can be:

* Displayed on screen
* Downloaded as CSV files

---

# Technology Stack

## Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* Maven
* Lombok

---

## Frontend

* React.js
* Axios
* React Router
* React Icons
* CSS

---

## Database

* H2 In-Memory Database

---

# Project Structure

```
enviro365-withdrawal-system
│
├── backend
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── exception
│   ├── mapper
│   ├── repository
│   ├── service
│   └── resources
│
├── frontend
│   ├── src
│   │   ├── api
│   │   ├── components
│   │   ├── pages
│   │   ├── style
│   │   └── App.jsx
│   └── package.json
│
├── README.md
├── AI_USAGE.md
└── API_DOCUMENTATION.md
```

---

# REST API Endpoints

## Authentication

| Method | Endpoint                   | Description    |
| ------ | -------------------------- | -------------- |
| POST   | `/api/auth/login/investor` | Investor login |

---

## Investors

| Method | Endpoint              | Description          |
| ------ | --------------------- | -------------------- |
| POST   | `/api/investors`      | Create investor      |
| GET    | `/api/investors`      | Get all investors    |
| GET    | `/api/investors/{id}` | Get investor profile |
| PUT    | `/api/investors/{id}` | Update investor      |
| DELETE | `/api/investors/{id}` | Delete investor      |

---

## Portfolios

| Method | Endpoint                                | Description             |
| ------ | --------------------------------------- | ----------------------- |
| POST   | `/api/portfolios`                       | Create portfolio        |
| GET    | `/api/portfolios/{id}`                  | Get portfolio           |
| GET    | `/api/portfolios/investor/{investorId}` | Get investor portfolios |
| DELETE | `/api/portfolios/{id}`                  | Delete portfolio        |

---

## Withdrawals

| Method | Endpoint                                   | Description                    |
| ------ | ------------------------------------------ | ------------------------------ |
| POST   | `/api/withdrawals`                         | Submit withdrawal              |
| GET    | `/api/withdrawals/portfolio/{portfolioId}` | Portfolio withdrawal history   |
| GET    | `/api/withdrawals/investor/{investorId}`   | Investor withdrawal history    |
| POST   | `/api/withdrawals/report/{investorId}`     | Generate withdrawal report     |
| POST   | `/api/withdrawals/export/{investorId}`     | Export withdrawal report (CSV) |

---

# Application Architecture

```
                React Frontend
                       │
                 Axios REST Calls
                       │
                Spring Boot API
                       │
       Controllers → Services → Repositories
                       │
                  H2 Database
```

---

# Validation

The application performs validation using:

* Jakarta Bean Validation
* Spring Validation
* Custom Business Rules
* Global Exception Handling

---

# Exception Handling

A global exception handler provides consistent error responses.

Handled exceptions include:

* Resource Not Found
* Business Rule Violations
* Validation Errors
* Unexpected Server Errors

---

# Sample Data

The application includes sample SQL data containing:

* Investors
* Portfolios
* Products
* Withdrawals

To populate the tables run the sql document.sql on H2 environment.

---

# Running the Backend

## Requirements

* Java 21
* Maven

### Start the backend

bash
cd enviro365backend

mvn spring-boot:run


The API starts at
http://localhost:8080

H2 Console
http://localhost:8080/h2-console

---

# Running the Frontend

## Requirements

* Node.js
* npm

Install dependencies

bash
npm install


Run the application

bash
npm run dev


The frontend starts at
http://localhost:5173

# Testing

The application has been tested for:

* Investor login
* Portfolio retrieval
* Investor profile update
* Withdrawal creation
* Business rule validation
* Withdrawal history
* Report generation
* CSV export
* Exception handling
* REST API communication
* Frontend and backend integration

---

# Future Improvements

Possible future enhancements include:

* JWT Authentication
* Spring Security
* Role-based access control
* PDF report generation
* Email notifications
* Audit logging
* Pagination
* Search functionality
* Docker deployment
* MySQL/PostgreSQL production database
* Cloud deployment (AWS/Azure)

---

# AI Usage

Artificial Intelligence (ChatGPT by OpenAI) was used as a development support tool for:

* Learning concepts
* Debugging
* Code review
* Documentation
* Best practice recommendations

All generated content was reviewed, tested, and adapted before inclusion in the final project.

For more information, see **README.md**.

---

# Author

**Lethabo Calvin Mampa**

Computer Science Student

Tshwane University of Technology

---

# Assessment

This project was developed as part of the **Enviro365 Junior Java Developer Assessment** and demonstrates knowledge of:

* Java
* Spring Boot
* REST API Development
* React.js
* H2 Database
* JPA/Hibernate
* DTO Design Pattern
* Exception Handling
* Business Rule Validation
* Full-Stack Application Development

---

# Thank You

Thank you for taking the time to review this submission.

I appreciate the opportunity to complete this assessment and demonstrate my technical skills and passion for software development. I hope this project reflects my ability to build clean, maintainable, and user-focused applications while following modern Java and React development practices.
