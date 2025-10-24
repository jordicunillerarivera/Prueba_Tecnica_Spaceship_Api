# Spaceship API

Proyecto Spring Boot (Java 25) — API CRUD para naves espaciales (series/películas).

---

# ¿Qué hace?

API REST para gestionar naves espaciales con:

* Persistencia en H2 (migraciones con Flyway + datos de ejemplo).
* Endpoints CRUD + búsqueda por nombre (paginated).
* Validación de entrada y manejo centralizado de excepciones.
* Caching (Caffeine) en la capa de servicio.
* Aspect para logging (ids negativos).
* Seguridad HTTP Basic (usuario en memoria).
* Publicación de eventos a Kafka.
* Documentación OpenAPI (Swagger).
* Tests unitarios/integración y JaCoCo para coverage.

---

# Endpoints (base `/api/spaceships`)

|   Método | Endpoint                                      | Descripción                                   |
| -------: | :-------------------------------------------- | :-------------------------------------------- |
|    `GET` | `/api/spaceships?page=&size=`                 | Listado paginado (devuelve `PagedResponse`)   |
|    `GET` | `/api/spaceships/{id}`                        | Obtener nave por id                           |
|    `GET` | `/api/spaceships/search?name=...&page=&size=` | Buscar por nombre (parcial, case-insensitive) |
|   `POST` | `/api/spaceships`                             | Crear nave (JSON body)                        |
|    `PUT` | `/api/spaceships/{id}`                        | Actualizar nave (JSON body)                   |
| `DELETE` | `/api/spaceships/{id}`                        | Eliminar nave                                 |

Otros recursos:

* Swagger UI: `/swagger-ui/index.html`
* OpenAPI JSON: `/v3/api-docs`

---

# Credenciales 

* Usuario: `admin`
* Password: `password`

Ejemplo `curl`:

```bash
curl -u admin:password http://localhost:8080/api/spaceships
```

---

# Enlaces de interés (local)

* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* OpenAPI JSON: `http://localhost:8080/v3/api-docs`
* Informe JaCoCo (local): `target/site/jacoco/index.html` (generado por `mvn test` + `mvn jacoco:report`)

---

# Docker — build & run 


1. Construir imagen:

```bash
docker build -t spaceship-api .
```

2. (Si ya existe un contenedor con el mismo nombre, eliminarlo primero)

```bash
docker rm -f spaceship-api-container || true
```

3. Ejecutar contenedor (mapeando puerto 8080):

```bash
docker run -d -p 8080:8080 --name spaceship-api-container spaceship-api
```

4. Verificar y ver logs:

```bash
docker ps
docker logs -f spaceship-api-container
```

---


# Kafka 

* Todos los eventos de create, update y delete se publican al topico `spaceship-events`.

---

# Tests y cobertura

* Ejecutar tests:

```bash
mvn test
```

* Generar JaCoCo:

```bash
mvn jacoco:report
# abrir target/site/jacoco/index.html
```
