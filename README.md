# The CRM Service

![workflow](https://github.com/javintx/the-crm-service/actions/workflows/gradle.yml/badge.svg)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=javintx_the-crm-service)
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
- The identifier (field id) of a customer is provided in the create customer request.
- The only field that is not possible to change from a customer is the identifier (field id).
- When a customer is deleted, it is removed physically from database.

---

## Architecture

- Language code: Java 11 with [Gradle](https://gradle.org)
- Architecture: [Hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
    - App: The main application layer. Contains the initialization of the CRM service.
    - Core: The business logic layer. Contains the use cases and the domain objects for the CRM service.
    - Persistence layer: First approach with in-memory storage. In the next iteration, it could
      be [H2](https://www.h2database.com/html/main.html).
    - Rest API layer: First approach with [SparkJava](https://sparkjava.com). Pending to publish API.
        - Authentication: First approach with all call are authenticated. In the next iteration, it could
          be [OAuth2](https://oauth.net/code/java/).
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

- List all customers
    - GET /customer/all
- Create new customer (with Customer JSON as body)
    - POST /customer/create
- Update customer (with Customer JSON as body)
    - PUT /customer/update
- Delete customer
    - DELETE /customer/delete/{customerId} (customerId is a path parameter)

### Customer JSON

> {
> "id": "identifier",
> "name": "name",
> "surname": "surname",
> "photo": "photo"
> }
