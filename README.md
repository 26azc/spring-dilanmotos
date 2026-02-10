# 🚀 Spring DilanMotos

Sistema de gestión de motos desarrollado con **Spring Boot**.  
Este proyecto permite administrar información relacionada con motos, clientes y servicios, integrando backend en Java y vistas en HTML.

---

## 📋 Características
- CRUD de motos (crear, leer, actualizar, eliminar).
- Gestión de clientes y servicios.
- Arquitectura basada en capas: `controller`, `service`, `repository`, `model`.
- Persistencia con base de datos relacional (MySQL/PostgreSQL).
- API RESTful propia, lista para ser consumida por cualquier frontend.

---

## 🛠️ Tecnologías utilizadas
- **Java 17+**
- **Spring Boot**
- **Maven**
- **MySQL/PostgreSQL**
- **Thymeleaf / HTML / CSS**

---

## ⚙️ Instalación y ejecución
1. Clona el repositorio:
   ```bash
   git clone https://github.com/26azc/spring-dilanmotos.git
Accede al directorio:

bash
cd spring-dilanmotos
Compila y ejecuta con Maven:

bash
mvn spring-boot:run
Accede en tu navegador:

Código
http://localhost:8080
📌 Endpoints principales
Motos
GET /motos → Lista todas las motos

POST /motos → Crea una nueva moto

GET /motos/{id} → Obtiene una moto por ID

PUT /motos/{id} → Actualiza una moto existente

DELETE /motos/{id} → Elimina una moto

Clientes
GET /clientes → Lista todos los clientes

POST /clientes → Crea un nuevo cliente

GET /clientes/{id} → Obtiene un cliente por ID

PUT /clientes/{id} → Actualiza un cliente existente

DELETE /clientes/{id} → Elimina un cliente

Servicios
GET /servicios → Lista todos los servicios

POST /servicios → Crea un nuevo servicio

GET /servicios/{id} → Obtiene un servicio por ID

PUT /servicios/{id} → Actualiza un servicio existente

DELETE /servicios/{id} → Elimina un servicio
