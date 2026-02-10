# 🔐 Sistema de Ventas – Backend (Spring Boot)

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-Enabled-6DB33F?logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-DB-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JPA Hibernate](https://img.shields.io/badge/JPA-Hibernate-59666C?logo=hibernate&logoColor=white)](https://hibernate.org/)
[![API](https://img.shields.io/badge/API-REST-000000)](#)
[![Postman](https://img.shields.io/badge/Postman-API%20Testing-FF6C37?logo=postman&logoColor=white)](https://www.postman.com/)
[![Render](https://img.shields.io/badge/Render-Cloud%20Hosting-46E3B7?logo=render&logoColor=white)](https://render.com/)

Este proyecto es un sistema de ventas para el restaurante "Lo Justo", construido con **Spring Boot 3**, orientado a la gestión de ventas, usuarios y reportes, con **seguridad basada en roles**, persistencia en **MySQL** y arquitectura preparada para consumo desde el frontend **React**.

---

## Despliegue en Render
> Link: 

## 🚀 Descripción general

Este backend permite:

- Autenticación de usuarios con **Spring Security**
- Autorización por **roles** (`ADMIN`, `CAJA`, `MOZO`, `COCINA`)
- Gestión de ventas, clientes, productos y categorías
- Reportes agregados para dashboards
- Consumo vía **API REST**
- Integración directa con frontend React

---

## ⚙️ Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Postman

---

# 🧠 Arquitectura

## 🔐 Seguridad (Spring Security)

Se implementa seguridad basada en:

- **HTTP Basic Authentication**
- **BCrypt** para encriptación de contraseñas
- **Roles** con prefijo `ROLE_`
- Autorización a nivel de endpoint

Ejemplo de control de acceso:

```txt
ADMIN / CAJA / MOZO → Ventas
ADMIN / CAJA        → Productos y categorías
COCINA              → Listado de pedidos
```
Las reglas se definen de forma centralizada en **SecurityConfig.**

## 👤 Autenticación

Endpoints de autenticación:
- **POST /api/auth/register** → Registro de usuarios.
- **GET /api/auth/me** → Datos del usuario autenticado.

El endpoint /me es utilizado por el frontend para:

- Validar credenciales
- Obtener rol
- Construir navegación dinámica

## 🧩 UserDetails personalizado

Se utiliza CustomUserDetailsService para:

- Cargar usuarios desde base de datos.
- Mapear roles a GrantedAuthority.
- Integrarse con Spring Security de forma estándar.

## 📡 Documentación de Endpoints

### 📦 Categorías
- Listado de categorías
    ```bash
    GET /api/categorias
    ```

### 🧑 Clientes
- Registro o verificación automática.
- Búsqueda por nombre.
    ```bash
    POST /api/clientes
    GET  /api/clientes/buscar?nombre=
    ```

### 🛍️ Productos

- Listado por categoría
    ```bash
    GET /api/productos/categoria/{id}
    ```

### 🧾 Ventas

- Crear venta
- Actualizar estado
- Listar ventas
- Consultar ventas del día

    ```bash
    POST /api/ventas
    PUT  /api/ventas/{id}
    GET  /api/ventas/listar-ventas
    GET  /api/ventas/hoy
    ```

### 📊 Reportes

- Endpoints diseñados para dashboards:

    ```bash
    GET /api/reportes/ventas-hoy
    GET /api/reportes/pedidos-hoy
    GET /api/reportes/productos-hoy
    GET /api/reportes/ventas-mensuales
    GET /api/reportes/clientes-total
    ```
Estos endpoints devuelven datos agregados optimizados para gráficos.

### 🌐 CORS y comunicación con frontend

- Configuración explícita de CORS:
- Origen permitido: http://localhost:5173
- Métodos: GET, POST, PUT, DELETE, OPTIONS
- Credenciales habilitadas