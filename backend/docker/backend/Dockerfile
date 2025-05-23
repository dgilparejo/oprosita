# Etapa 1: Compilación
FROM maven:3.8.1-openjdk-17 AS build
WORKDIR /app

# Copiar únicamente el pom.xml para descargar dependencias
COPY app/pom.xml ./pom.xml

# Copiar el resto del código fuente
COPY app/src ./src

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa anterior
COPY --from=build /app/target/*-SNAPSHOT.jar /app/myapp.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/myapp.jar"]