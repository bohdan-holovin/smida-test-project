Here's a draft for your `README.md`:

```markdown
# Smida Test Project

## Overview

The Smida Test Project is a comprehensive application designed to manage company data efficiently. The project leverages Spring Boot for its backend development, ensuring robust and scalable performance. This repository includes the essential features such as JWT authentication, CRUD operations for company entities, and thorough testing using JUnit.

## Features

- **JWT Authentication:** Secure the application endpoints using JSON Web Tokens.
- **Company Management:** Create, read, update, and delete company information.
- **User and Admin Roles:** Different access levels for user and admin roles.
- **API Documentation:** Swagger configuration for easy API documentation.
- **Testing:** Comprehensive unit and integration tests using JUnit.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **JUnit**
- **Swagger**
- **MongoDB**
- **Docker**

## Project Structure

```
smida-test-project
│   README.md
│   pom.xml
└───src
    └───main
    │   └───java
    │   │   └───com
    │   │       └───example
    │   │           └───smida
    │   │               └───controller
    │   │               └───model
    │   │               └───repository
    │   │               └───service
    │   │               └───util
    │   └───resources
    │       └───application.properties
    └───test
        └───java
            └───com
                └───example
                    └───smida
                        └───controller
                        └───service
                        └───util
```

## Getting Started

### Prerequisites

- Java 17
- Docker
- MongoDB

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/bohdan-holovin/smida-test-project.git
    cd smida-test-project
    ```

2. Build the project:
    ```sh
    ./mvnw clean install
    ```

3. Run the project:
    ```sh
    ./mvnw spring-boot:run
    ```

### Running Tests

To run the tests, use the following command:
```sh
./mvnw test
```

## API Endpoints

### CompanyController

- **Get All Companies**
  - `GET /api/companies`
  - **Roles:** `USER`, `ADMIN`

- **Get Company by ID**
  - `GET /api/companies/{id}`
  - **Roles:** `USER`, `ADMIN`

- **Create Company**
  - `POST /api/companies`
  - **Roles:** `ADMIN`

- **Update Company**
  - `PUT /api/companies/{id}`
  - **Roles:** `ADMIN`

- **Delete Company**
  - `DELETE /api/companies/{id}`
  - **Roles:** `ADMIN`

## Configuration

### JWT Configuration

Ensure you have the correct properties set in your `application.properties`:
```properties
jwt.secret=<your_jwt_secret>
jwt.expiration=<jwt_expiration_time>
```

### CORS Configuration

The `CorsConfig` class is configured to allow cross-origin requests from specific origins.

## Contribution

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries, please contact [Bohdan Holovin](mailto:your-email@example.com).
```

Feel free to adjust any sections as necessary for your project specifics.
