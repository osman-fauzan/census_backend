# CensusDBService Backend

A robust backend service for managing and querying census records using Java, JDBC, and MySQL stored procedures.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Configuration](#configuration)
- [Data Model](#data-model)
- [CRUD Operations](#crud-operations)
- [Transaction Management](#transaction-management)
- [Error Handling](#error-handling)
- [Getting Started](#getting-started)
- [License](#license)

---

## Overview

**CensusDBService** is the core backend component of the Census Management System. It handles database connectivity, executes CRUD operations through MySQL stored procedures, and maps results to Java domain objects for seamless integration.

---

## Features

- JDBC-based connection management
- CRUD operations via MySQL stored procedures
- Clean separation of database and domain models
- Safe resource handling with try-with-resources
- Transaction encapsulation within stored procedures
- Clear, user-friendly error messaging

---

## Architecture

- **Language:** Java
- **Database:** MySQL
- **Connector:** JDBC (`mysql-connector-j`)
- **Main Class:** `CensusDBService`
- **Stored Procedures:** Handles insert, retrieve, update, and delete operations

---

## Configuration

1. **Database Properties File (`db.properties`):**
    ```properties
    db.url=jdbc:mysql://localhost:3306/census_db
    db.user=census_user
    db.password=census_password
    db.driver=com.mysql.cj.jdbc.Driver
    ```
2. **JDBC Driver:**
   Make sure `mysql-connector-j-<version>.jar` is on your classpath.

---

## Data Model

- **CensusRecord:** POJO representing a census record, including person and officer fields.
- **CensusOperationResult:** Holds operation messages and a list of `CensusRecord` objects.

---

## CRUD Operations

### Insert

Executes the `sp_insert_census_record` stored procedure to add a new record.

### Retrieve

Fetches records via `sp_retrieve_census_records` with flexible filtering.

### Update

Updates an existing record using `sp_update_census_record`.

### Delete

Removes a record using `sp_delete_census_record`.

All CRUD operations use strongly-typed methods and map results to Java objects.

---

## Transaction Management

All database transactions are managed within MySQL stored procedures, ensuring atomic and consistent operations.

---

## Error Handling

- All JDBC operations use try-with-resources for automatic resource cleanup.
- Errors are caught, logged, and translated into clear messages via `CensusOperationResult`.

---

## Getting Started

1. **Clone the Repository**
    ```bash
    git clone https://github.com/osman-fauzan/census_backend.git
    cd census_backend
    ```

2. **Configure Database**
    - Set up your MySQL database and import the required schema and stored procedures.
    - Update `db.properties` with your database credentials.

3. **Build the Project**
    - Use your preferred Java build tool (e.g., Maven or Gradle).
    - Ensure dependencies for MySQL JDBC driver are resolved.

4. **Run the Service**
    - Instantiate `CensusDBService` in your application and use the provided methods for CRUD operations.

---

## License

MIT

---

For questions or contributions, please open an issue or pull request!