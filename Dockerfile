# --- Etapa 1: Compilación ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copiar el POM y descargar las dependencias (se queda en caché si no cambias el pom)
COPY pom.xml .
RUN mvc_go_offline=true mvn dependency:go-offline -B

# Copiar el código fuente y compilar el archivo JAR saltando los tests para acelerar
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Ejecución ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que corre tu API
EXPOSE 8080

# Comando para arrancar tu backend
ENTRYPOINT ["java", "-jar", "app.jar"]