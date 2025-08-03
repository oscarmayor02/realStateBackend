
# Etapa 1: Construcción con Maven y JDK 17
FROM maven:3.8.7-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar solo los archivos necesarios para cachear las dependencias primero
COPY pom.xml .
COPY src ./src

# Construir el paquete sin tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecutar la app
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el .jar generado de la etapa build
COPY --from=build /app/target/realEstate-backend-0.0.1-SNAPSHOT.jar app.jar
# Exponer el puerto que usa Spring Boot (puedes cambiar si usas otro)
EXPOSE 8080

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
