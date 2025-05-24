# Etapa 0: Extraer .jar de openapi image
ARG DOCKERHUB_USERNAME
FROM ${DOCKERHUB_USERNAME}/openapi:1.0.1 AS openapi

# Etapa 1: Compilación del backend
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY app/pom.xml ./pom.xml
COPY app/src ./src

# Copia el jar de openapi desde la imagen
COPY --from=openapi /openapi/target/openapi-1.0.1.jar /root/.m2/repository/com/oprosita/openapi/1.0.1/openapi-1.0.1.jar

# Compila el backend usando el openapi.jar que ahora ya existe en el repo local simulado
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa anterior
COPY --from=build /app/target/*-SNAPSHOT.jar /app/myapp.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/myapp.jar"]