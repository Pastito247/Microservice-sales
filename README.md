# 🛒 Microservicio de Ventas - Proyecto Final

Este microservicio gestiona ventas físicas y online, incluyendo órdenes, facturas y la integración con el microservicio de logística. Está desarrollado con Spring Boot, Docker, Swagger y pruebas unitarias con JUnit y Mockito.

---

## 📌 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Swagger / OpenAPI
- JUnit 5 + Mockito
- Docker / Docker Compose
- HATEOAS
- Lombok

---

## 🚀 Cómo levantar el proyecto

### 1. Asegúrate de que el microservicio **logística** esté corriendo localmente en el puerto `8081`.

```bash
cd ruta/del/microservicio/logistica
./mvnw spring-boot:run
