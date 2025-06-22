# Usa una imagen base con Java 17
FROM openjdk:17-jdk-slim

# Define el nombre del archivo JAR
ARG JAR_FILE=target/sales-0.0.1-SNAPSHOT.jar

# Copia el JAR al contenedor
COPY ${JAR_FILE} app.jar

# Expón el puerto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
