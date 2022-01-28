# The CRM Service

![workflow](https://github.com/javintx/the-crm-service/actions/workflows/gradle.yml/badge.svg)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=alert_status)](https://sonarcloud.io/dashboard?id=javintx_the-crm-service)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=javintx_the-crm-service)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=javintx_the-crm-service)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=javintx_the-crm-service)
[![SonarCloud Code Smells](https://sonarcloud.io/api/project_badges/measure?project=javintx_the-crm-service&metric=code_smells)](https://sonarcloud.io/component_measures?id=javintx_the-crm-service&metric=Maintainability&view=list)

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

## Assumptions

- None for now

## Architecture

- Language code: Java 11 with [Gradle](https://gradle.org)
- Architecture: [Hexagonal](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
    - Persistence layer: First approach with in-memory storage. In the next iteration, it could
      be [H2](https://www.h2database.com/html/main.html).
    - Rest API layer: First approach with Spark.
- Implementation: With Test-Driven Development ([TDD](https://en.wikipedia.org/wiki/Test-driven_development)).
- Unit test: [JUnit5](https://junit.org/junit5/) with [Mockito](https://site.mockito.org).


- The CRM Service application has been done respecting the [SOLID](https://en.wikipedia.org/wiki/SOLID)
  and [KISS](https://en.wikipedia.org/wiki/KISS_principle) principles and applying Clean Code as far as possible.

