FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /openapi

# Copia todo el backend y el openapi.yml
COPY . .
COPY ../openapi.yml ./openapi.yml

RUN mvn clean package -DskipTests

# ENTRYPOINT para que se pueda usar como generador desde "docker run"
ENTRYPOINT ["java", "-jar", "target/openapi-generator-cli.jar"]
