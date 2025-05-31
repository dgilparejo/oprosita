FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /openapi
COPY . .

RUN mvn clean package -DskipTests

# ENTRYPOINT para que se pueda usar como generador desde "docker run"
ENTRYPOINT ["java", "-jar", "target/openapi-generator-cli.jar"]
