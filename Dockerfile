# syntax=docker/dockerfile:1

FROM eclipse-temurin:11-jre-focal
WORKDIR /app
COPY ./build/libs/the-crm-service-1.0.0.jar .
EXPOSE 8080
CMD ["java", "-jar", "the-crm-service-1.0.0.jar"]
