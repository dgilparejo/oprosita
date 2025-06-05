# Etapa 0: Extraer .jar de openapi image
ARG DOCKERHUB_USERNAME
ARG OPENAPI_BACKEND_VERSION
FROM ${DOCKERHUB_USERNAME}/openapi-backend:${OPENAPI_BACKEND_VERSION} AS openapi

# Etapa 1: Compilación del backend
FROM maven:3.9.6-eclipse-temurin-21 AS build
ARG OPENAPI_BACKEND_VERSION
ENV OPENAPI_BACKEND_VERSION=${OPENAPI_BACKEND_VERSION}
WORKDIR /app

COPY app/pom.xml ./pom.xml
COPY app/src ./src

# Copia el jar de openapi desde la imagen anterior
COPY --from=openapi /openapi/target/openapi-backend-${OPENAPI_BACKEND_VERSION}.jar /root/.m2/repository/com/oprosita/openapi-backend/${OPENAPI_BACKEND_VERSION}/openapi-backend-${OPENAPI_BACKEND_VERSION}.jar

# Compila el backend usando el openapi.jar que ahora ya existe en el repo local simulado
RUN mvn clean package -DskipTests -Dopenapi.version.override=${OPENAPI_BACKEND_VERSION}

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/myapp.jar
CMD ["java", "-jar", "/app/myapp.jar"]
