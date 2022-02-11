# The CRM Service

![workflow](https://github.com/javintx/the-crm-service/actions/workflows/gradle.yml/badge.svg)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=javintx_the-crm-service)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=javintx_the-crm-service)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=javintx_the-crm-service)
[![SonarCloud Code Smells](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=code_smells)](https://sonarcloud.io/component_measures?id=javintx_the-crm-service&metric=Maintainability&view=list)

---

The objective is to create a REST API to manage customer data for a small shop. It will work as the backend side for a
CRM interface that is being developed by a different team. As the lead developer of the backend project, you'll be in
charge of the API design and implementation. Here are the requirements for the API:

- The API should be only accessible by a registered user by providing an authentication mechanism.
- A user can only:
  - List all customers in the database.
  - Get full customer information, including a photo URL.
  - Create a new customer:
    - A customer should have at least name, surname, id and a photo field.
    - Name, surname and id are required fields.
    - Image uploads should be able to be managed.
    - The customer should have a reference to the user who created it.
  - Update an existing customer.
    - The customer should hold a reference to the last user who modified it.
  - Delete an existing customer.
- An admin can also:
  - Manage users:
    - Create users.
    - Delete users.
    - Update users.
    - List users.
    - Change admin status.

---

## Assumptions

- The persistence was implemented with in-memory datastore.
  - The first admin user is in the datastore by default with `admin` id.
- The authentication was implemented with a JWT adapter.
  - Only provided a validation token method. The token should be generated with the same server secret to be valid.
- For a customer:
  - The identifier (field id) of a customer is provided in the create customer request.
  - The only field that is not possible to change from a customer is the identifier (field id).
  - When a customer is deleted, it is removed physically from database.
  - The user reference (field userId) must be passed with the customer information, and it will not be validated.
  - The photo field is opened to be whatever.
  - The header `userId` must be filled with the user identifier to have access to the REST API.
- For a user:
  - The identifier (field id) of a user is provided in the create customer request.
  - The only field that is not possible to change from a user is the identifier (field id).
  - When a user is deleted, it is removed physically from database. The customer will maintain the latest user id that
    modified it.
  - The mandatory fields for a user will be: id, name and surname.
  - The header `adminId` must be filled with the user identifier to have access to the REST API.
- For an admin:
  - Admin is a user with an activated flag.
- The headers `userId` and `adminId` are not intended for validation purposes.
- The header `Authorization` is to include the "Bearer" authentication token.

---

## Architecture

- Language code: Java 11 with [Gradle](https://gradle.org)
- Architecture: [Hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
  - App: The main application layer. Contains the initialization of the CRM service.
  - Core: The business logic layer. Contains the use cases and the domain objects for the CRM service.
  - Persistence layer: First approach with in-memory storage.
  - Rest API layer: First approach with [SparkJava](https://sparkjava.com). Pending to publish API.
    - Authentication: First approach with all call are authenticated.
    - CORS is activated.
    - Log: With [Slf4J](https://www.slf4j.org). This layer could be extracted to a module for all layers.
- Implementation: With Test-Driven Development ([TDD](https://en.wikipedia.org/wiki/Test-driven_development)).
- Unit test: [JUnit5](https://junit.org/junit5/) with [Mockito](https://site.mockito.org).
- End-to-end test: JUnit5 with [RestAssured](https://rest-assured.io).


- The CRM Service application has been done respecting the [SOLID](https://en.wikipedia.org/wiki/SOLID)
  and [KISS](https://en.wikipedia.org/wiki/KISS_principle) principles and applying Clean Code as far as possible.

---

## HOW-TO

### Build the project

In the `rootDirectory` execute:

> gradlew clean build

### Execute tests

In the `rootDirectory` execute:

> gradlew clean test

### Obtain JaCoCo report

In the `rootDirectory` execute:

> gradlew clean test jacocoTestReport

### Execute the application

In the `rootDirectory/build/libs` execute:

> java -jar the-crm-service-1.0.0.jar

--- 

## Docker

- To generate a Docker image with "the-crm-service" name after build the project, execute the next command:

> docker build . -t the-crm-service

- To run the Docker image "the-crm-service", execute the next command:

> docker run -p 8080:8080 -d -i --name CRM the-crm-service

The parameter `-p` publish a container's port(s) to the host. The parameter `-d` run container in background and print
container ID. The parameter `-i` keep STDIN open even if not attached. The parameter `--name` assign a name to the
container.

---

## REST API

The request header `Authorization` must be filled with a valid token to have access to the REST API.

### Customer REST API

The request header `userId` must be filled with the user identifier to have access to the customer REST API.

- List all customers
  - GET /customer/all
- Create new customer (with [Customer JSON](#customer-json) as body)
  - POST /customer/create
  - Could throw exception if customer already exists
- Update customer (with [Customer JSON](#customer-json) as body)
  - PUT /customer/update
  - Could throw exception if customer does not exist
- Delete customer
  - DELETE /customer/delete/{customerId} (customerId is a path parameter)
  - Could throw exception if customer does not exist

### Customer JSON

> {
> "id": "identifier",
> "name": "name",
> "surname": "surname",
> "photo": "photo",
> "userId": "userId"
> }

---

### User REST API

The request header `adminId` must be filled with the user identifier of an admin to have access to the user REST API.

- List all users
  - GET /user/all
- Create new user (with [User JSON](#user-json) as body)
  - POST /user/create
  - Could throw exception if user already exists
- Update user (with [User JSON](#user-json) as body)
  - PUT /user/update
  - Could throw exception if user does not exist
- Delete user
  - DELETE /user/delete/{userId} (userId is a path parameter)
  - Could throw exception if user does not exist

### User JSON

> {
> "id": "identifier",
> "name": "name",
> "surname": "surname",
> }

---

## Improvements

- The authentication package could be extracted to a module.
  - The current JWT adapter could be improved by defining more methods, classes and endpoints.
- The authentication layer could implement an adapter for [OAuth2](https://oauth.net/code/java/).
  - It should be generated an end point to generate the authorization token.
- The persistence layer could implement an adapter for [H2](https://www.h2database.com/html/main.html).
- It could be added [OpenAPI](https://www.openapis.org) or [Swagger](https://swagger.io/specification/) to publish the
  REST API in a better way than in this README.md.
- It could be added a deletion flag for customer or user to do not make a physical delete and keep the historic data.
- The user reference in the customer could be validated with persistence and could be extracted from the request.
- Check that if the only test are the e2e test, the coverage does not change.
