# 🌌 SWAPI Spring Boot App

Este proyecto es una **API REST** desarrollada en **Java 8** con **Spring Boot** que actúa como un **proxy/cliente** de la [Star Wars API (SWAPI)](https://www.swapi.tech/).\
Permite obtener información sobre personajes, recursos, búsqueda por nombre e ID, con manejo de paginación y errores.

---

## 📋 Tecnologías utilizadas

- **Java 8**
- **Spring Boot 2.x**
- **Spring Web**
- **Spring Security** (con autenticación básica)
- **Jackson** (para serialización/deserialización JSON)
- **JUnit 5** y **Mockito** (para tests)
- **Maven**

---

## ⚙️ Configuración y ejecución

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/usuario/swapi-app.git
   cd swapi-app
   ```

2. Compilar y ejecutar:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. La aplicación se levanta en:

   ```
   http://localhost:8080/api
   ```

---

## 🔑 Autenticación

La aplicación está protegida con **Spring Security**.\
Para acceder a los endpoints es necesario autenticarse con **Basic Auth**.
Los usuarios son In Memory y se encuentran en el file SecurityConfig.

- **Usuario:** `luke`
- **Password:** `force`

Si no te autenticás, recibirás un `403 Forbidden`.

---

## 🚀 Endpoints disponibles

### 1. Obtener recursos

```http
GET /api/{resource}?page={page}&limit={limit}
```

Ejemplo:

```
GET /api/people?page=1&limit=10
```

📌 Devuelve una lista paginada de recursos (ej: people).

---

### 2. Buscar por nombre

```http
GET /api/{resource}/name/{name}?page={page}&limit={limit}
```

Ejemplo:

```
GET /api/people/name/Luke?page=1&limit=10
```

📌 Devuelve los resultados filtrados por nombre.

---

### 3. Obtener por ID

```http
GET /api/{resource}/id/{id}
```

Ejemplo:

```
GET /api/people/id/1
```

📌 Devuelve el detalle de un recurso específico.

---

## 📦 Ejemplo de respuesta

```json
{
  "total_records": 82,
  "total_pages": 9,
  "previous": null,
  "next": "https://www.swapi.tech/api/people?page=2&limit=10",
  "results": [
    {
      "name": "Luke Skywalker",
      "height": "172",
      "mass": "77",
      "hair_color": "blond"
    }
  ]
}
```

---

## 🛡️ Manejo de errores

La API devuelve errores controlados con `ResponseEntity`:

- `400 Bad Request` → Petición inválida.
- `403 Forbidden` → Usuario no autenticado o sin permisos.
- `404 Not Found` → Recurso no encontrado.
- `500 Internal Server Error` → Error inesperado.

---

## 🧪 Testing

El proyecto incluye **tests unitarios** con JUnit 5 y Mockito.

Para ejecutarlos:

```bash
mvn test
```

Los tests validan:

- Controladores (`ResourceControllerTest`)
- Integración con el servicio (`SwapiServiceTest`)
- Manejo de excepciones

---

## 📂 Estructura del proyecto

```
src
 ├─ main
 │   ├─ java/com/swapi
 │   │   ├─ controller   → Controladores REST
 │   │   ├─ dto          → Objetos de transferencia (DTOs)
 │   │   ├─ service      → Lógica de negocio y conexión con SWAPI
 │   │   └─ security     → Configuración de Spring Security
 │   └─ resources
 │       └─ application.properties
 └─ test
     └─ java/com/swapi   → Tests con JUnit + Mockito
```

---
