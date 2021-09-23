[![CircleCI](https://circleci.com/gh/theagoliveira/spring5-webflux-rest.svg?style=shield)](https://circleci.com/gh/theagoliveira/spring5-webflux-rest) [![codecov](https://codecov.io/gh/theagoliveira/spring5-webflux-rest/branch/main/graph/badge.svg?token=CZD0L7R0J0)](https://codecov.io/gh/theagoliveira/spring5-webflux-rest)

# Spring Framework — Spring WebFlux RESTful WebService

Project from the online course [**Spring Framework 5: Beginner to Guru**](https://www.udemy.com/course/spring-framework-5-beginner-to-guru/) by John Thompson — Section 26.

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Contents](#contents)
- [Project Summary](#project-summary)
- [API Documentation](#api-documentation)
  - [Customers](#customers)
  - [Categories](#categories)
- [Deployment](#deployment)

## Contents

- Introduction
- New Spring Boot Project
- Creating Data Model and Repositories
- Create Vendor Data Model, Populate Data
- Create Category Controller
- Testing Category Controller
- Create Get Endpoints for Vendors
- Create Category with POST
- Update Category with PUT
- Implement POST for Vendors
- Implement PUT for Vendors
- Update Category with PATCH
- Implement PATCH for Vendors
- Conclusion

## Project Summary

- Project: Gradle
- Java: 11
- Spring Boot: 2.5.0
- Dependencies
  - Spring Data Reactive MongoDB
  - Spring Reactive Web
  - Spring Boot Starter Test
  - Embedded MongoDB Database
  - Reactor Test
  - Lombok
  - Spring Boot DevTools
  - Spring Security
- Plugins
  - Spring Boot
  - Spring Dependency Management
  - Java
  - JaCoCo

## API Documentation

### Customers

#### Get Customers

Retrieves a list of all customers.

##### Request

```http
GET /api/v1/customers
```

##### Sample Response (200 OK)

```json
[
    {
        "id": "614bf84ede8d0275d4cdc884",
        "first_name": "Alice",
        "last_name": "Foo"
    },
    {
        "id": "614bf8c8de8d0275d4cdc886",
        "first_name": "Bob",
        "last_name": "Bar"
    }
]
```

#### Get Customer by ID

Retrieves one customer.

##### Request

```http
GET /api/v1/customers/:id
```

##### Sample Response (200 OK)

```json
{
    "id": "60c060942f63c12e4a1bb782",
    "first_name": "First",
    "last_name": "Last"
}
```

#### Add Customer

Add a new customer to the application.

##### Request

```http
POST /api/v1/customers
```

##### Sample Request Body

```json
{
    "name": "Test"
}
```

##### Sample Response (201 CREATED)

#### Update or Add Customer

Update customer information by ID or create a new customer if the ID does not exist.

##### Request

```http
PUT /api/v1/customers/:id
```

##### Sample Request Body

```json
{
    "name": "Updated or Created Customer"
}
```

##### Sample Response (200 OK)

```json
{
    "id": "614bf84bde8d0275d4cdc883",
    "name": "Updated or Created Customer"
}
```

#### Update Existing Customer

Update existing customer information by ID.

##### Request

```http
PATCH /api/v1/customers/:id
```

##### Sample Request Body

```json
{
    "name": "Updated Customer"
}
```

##### Sample Response (200 OK)

```json
{
    "id": "614bf84bde8d0275d4cdc883",
    "name": "Updated Customer"
}
```

##### Error

If the customer does not exist, the server returns a 500 Internal Server Error.

### Categories

#### Get Categories

Retrieves a list of all categories.

##### Request

```http
GET /api/v1/categories
```

##### Sample Response (200 OK)

```json
[
    {
        "id": "60c060942f63c12e4a1bb77d",
        "name": "Category A"
    },
    {
        "id": "60c060942f63c12e4a1bb77e",
        "name": "Category B"
    }
]
```

#### Get Category by ID

Retrieves one category.

##### Request

```http
GET /api/v1/categories/:id
```

##### Sample Response (200 OK)

```json
{
    "id": "60c060942f63c12e4a1bb77d",
    "name": "Category"
}
```

#### Add Category

Add a new category to the application.

##### Request

```http
POST /api/v1/categories
```

##### Sample Request Body

```json
{
    "first_name": "Test",
    "last_name": "Test"
}
```

##### Sample Response (201 CREATED)

#### Update or Add Category

Update category information by ID or create a new category if the ID does not exist.

##### Request

```http
PUT /api/v1/categories/:id
```

##### Sample Request Body

```json
{
    "first_name": "Updated or Created",
    "last_name": "Category"
}
```

##### Sample Response (200 OK)

```json
{
    "id": "614bf84ede8d0275d4cdc884",
    "first_name": "Updated or Created",
    "last_name": "Category"
}
```

#### Update Existing Category

Update existing category information by ID.

##### Request

```http
PATCH /api/v1/categories/:id
```

##### Sample Request Body

```json
{
    "first_name": "Updated",
    "last_name": "Category"
}
```

##### Sample Response (200 OK)

```json
{
    "id": "614bf84ede8d0275d4cdc884",
    "first_name": "Updated",
    "last_name": "Category"
}
```

##### Error

If the category does not exist, the server returns a 500 Internal Server Error.

## Deployment

API deployed on [Heroku](https://aqueous-forest-02764.herokuapp.com/)
