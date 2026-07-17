# Project Task Tracker

## Overview

Project Task Tracker is a Java console application developed to help software development teams manage employees and project tasks.

The application allows users to:

- Log into the system
- Add employees
- Search and list employees
- Create tasks
- Assign tasks to employees
- Update task status

The project demonstrates Java Object-Oriented Programming concepts, exception handling, collections, and software design principles.

---

## Features

### Login
- Username and password authentication
- Prevents unauthorized access

### Employee Management
- Add new employees
- Search employees by Employee ID
- List all employees

### Task Management
- Create new tasks
- Set priority (LOW, MEDIUM, HIGH)
- Set due date
- Prevent duplicate Task IDs

### Task Assignment
- Search task by Task ID
- Assign an employee to a task
- Validate employee existence
- One employee may have multiple tasks
- One task can only be assigned to one employee

### Task Status Update
- Update task status
- Available statuses:
  - OPEN
  - IN_PROGRESS
  - COMPLETED

---

## Project Structure

```
src
└── main
    └── java
        └── org
            └── tasktracker
                ├── model
                │   ├── Employee.java
                │   ├── Task.java
                │   ├── Priority.java
                │   └── Status.java
                │
                ├── manager
                │   ├── EmployeeManager.java
                │   ├── TaskManager.java
                │   └── LoginManager.java
                │
                ├── exceptions
                │   ├── EmployeeNotFoundException.java
                │   ├── TaskNotFoundException.java
                │   ├── DuplicateTaskIdException.java
                │   └── InvalidStatusException.java
                │
                └── TaskTracker.java
```

---

## Technologies Used

- Java
- Java Collections (HashMap, ArrayList)
- Java Time API (LocalDate)
- Exception Handling
- Object-Oriented Programming (OOP)

---

## Object-Oriented Concepts Applied

### Encapsulation
- Private class attributes
- Public getters and setters

### Abstraction
- Business logic separated into manager classes

### Composition
- TaskManager depends on EmployeeManager

### Enumerations
- Status
- Priority

### Exception Handling
- EmployeeNotFoundException
- TaskNotFoundException
- DuplicateTaskIdException
- InvalidStatusException

---

## Data Model

### Employee

| Field | Type |
|--------|------|
| Employee ID | String |
| Employee Name | String |
| Department | String |

### Task

| Field | Type |
|--------|------|
| Task ID | String |
| Task Name | String |
| Priority | Priority |
| Status | Status |
| Assigned Employee | Employee |
| Due Date | LocalDate |

---

## Business Rules

- Employee ID must be unique.
- Task ID must be unique.
- Employee must exist before task assignment.
- Task must exist before assignment.
- A task can only be assigned to one employee.
- An employee may have multiple tasks.
- Only valid task statuses are accepted.

---

## Custom Exceptions

| Exception | Description |
|------------|-------------|
| EmployeeNotFoundException | Employee ID does not exist |
| TaskNotFoundException | Task ID does not exist |
| DuplicateTaskIdException | Duplicate Task ID entered |
| InvalidStatusException | Invalid task status entered |

---

## UML Diagrams

The project includes:

- Class Diagram
- Entity Relationship Diagram (ERD)

---

## Sample Workflow

1. Login
2. Add Employee
3. Create Task
4. Search and Assign Task
5. Update Task Status
6. Exit

---

## Requirements

- JDK 21 or later

Verify Java installation:

```bash
java -version
```

---
## Running the Application

Clone the repository:

```bash
git clone https://github.com/<your-username>/task-tracker.git
```

Navigate to the project folder:

```bash
cd task-tracker
```

Run the application:

```bash
./gradlew --console=plain run
```

Windows:

```bash
gradlew.bat run
```

---

## Default Login Credentials

Username:

```
admin
```

Password:

```
password123
```


