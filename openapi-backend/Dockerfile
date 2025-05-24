FROM maven:3.9.6-eclipse-temurin-21 AS openapi
WORKDIR /openapi

COPY . .
RUN mvn clean install -DskipTests
