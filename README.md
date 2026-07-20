# Tech_Assess-EMS

## Table of Contents

- [Overview](#overview)
- [Features](#features)
  - [Entity Relationship Diagram (ERD)](#entity-relationship-diagram-erd)
  - [Class diagram](#class-diagram)
  - [Swagger Documentation](#swagger-documentation)
  - [App Pages](#app-pages)
- [Technologies Used](#technologies-used)
  - [Backend](#backend)
  - [Frontend](#frontend)
- [Getting Started](#getting-started)
- [Contributors](#contributors)

## Overview

A web-based Employee Management System that allows users to manage
employee records, departments, and project assignments within an organization.

## Features

The system supports the following operations:

- Create new departments with name, location, and budget information
- View all departments in the system
- Update department information
- Delete departments (only if no employees are assigned)
- Add new employees with personal information (name, email, phone, hire date, salary)
- Assign employees to departments
- View all employees or filter by department
- Update employee information
- Remove employees from the system
- Create projects with name, description, start date, and end date
- Assign multiple employees to a project
- Assign projects to departments
- View all projects or filter by department
- Update project details
- Delete projects
- An employee can be assigned to multiple projects
- A project can have multiple employees
- Track the role of each employee in each project (e.g., Developer, Manager, Analyst)
- View all projects for a specific employee
- View all employees working on a specific project

### Entity Relationship Diagram (ERD)

## ![ERD](ERD/ERD.png)

### Class diagram

## ![Class diagram](ERD/tech_assess.png)

### Swagger Documentation

#### Employee Controller

![Employee Controller](swagger/Employee%20Controller.png)

#### Project Controller

![Project Controller](swagger/Project%20Controller.png)

#### Employee Project & Department Controllers

## ![Employee Project & Department Controllers](swagger/Employee%20Project%20&%20Department%20Controllers.png)

### App Pages

#### Departments Page

##### Departments list

![Departments list](app%20pages/Departments%20list.png)

##### Create Department

## ![Create Department](app%20pages/Create%20Department.png)

#### Employees Page

##### Employees list

![Employees list](app%20pages/Employees%20list.png)

##### Create Employee

## ![Create Employee](app%20pages/Create%20Employee.png)

#### Projects Page

##### Projects list

![Projects list](app%20pages/Project%20list.png)

##### Create Project

## ![Create Project](app%20pages/Create%20Project.png)

#### Employee Projects Page

##### Employee Projects list

![Employee Projects list](app%20pages/Employee%20Projects%20list.png)

##### Assign Employee to Project

![Assign Employee to Project](app%20pages/Assign%20Employee%20to%20Project.png)

## Technologies Used

### Backend

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

### Frontend

- **Angular**
- **TypeScript**
- **Bootstrap**
- **Standalone Components**
- **Signals**
- **Angular Router**
- **Reactive Forms**
- **Lazy Loading**

## Getting Started

To get started with the Employee Management System project, follow the setup instructions in the respective directories:

- [Backend Setup Instructions](backend/README.md)
- [Frontend Setup Instructions](frontend/README.md)

## Contributors

- [Mustafa Zayed](https://github.com/Mustafa-Zayed)
