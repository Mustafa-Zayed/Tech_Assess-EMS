# Employee Management System - Backend

## Overview

The backend of the Employee Management System is responsible for handling all server-side operations including managing departments, employees and projects through a RESTful API. It handles business logic, validation, database persistence, and API documentation while following a layered architecture and Spring Boot best practices.

## Technologies Used

- **Java 25**
- **Spring Boot 4**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Jakarta Bean Validation**
- **OpenAPI & Swagger UI**
- **Lombok**
- **Maven**
- **ModelMapper**

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/Mustafa-Zayed/Tech_Assess-EMS.git
```

### 2. Navigate to the backend directory

```bash
cd backend
```

### 3. Configure the database

Update your `application.properties` file with your PostgreSQL credentials.

```properties
spring.datasource.url = jdbc:postgresql://localhost:5432/tech_assess
spring.datasource.username = root
spring.datasource.password = your_password
```

### 4. Install dependencies (assuming Maven is installed)

```bash
mvn clean install
```

### 5. Run the application

```bash
mvn spring-boot:run
```

or with the current version from the `pom.xml` file

```bash
java -jar target/backend-x.x.x.jar
```

### 6. Access the API documentation using Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

## Additional Resources

The repository includes additional resources to help you get started:

### Database Script

The `tech_assess.sql` file contains the PostgreSQL database schema and sample data.

To import it:

```bash
psql -U postgres -d tech_assess -f tech_assess.sql
```

Alternatively, you can import it using pgAdmin or your preferred PostgreSQL client.

The file is located at: database/tech_assess.sql

### Postman Collection

The `Tech_Assess-EMS.postman_collection.json` file contains a complete collection of the available REST API endpoints ready to test.

Import the collection into Postman to test the API without manually creating requests.

The Postman collection is located at: postman/Tech_Assess-EMS.postman_collection.json

## Contributors

- [Mustafa Zayed](https://github.com/Mustafa-Zayed)
