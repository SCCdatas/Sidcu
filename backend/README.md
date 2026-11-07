# SIDCU Backend

API REST para **orientar a estudiantes en la elección de su carrera y universidad**. Un estudiante registra en qué carreras está interesado; el sistema sabe qué universidades ofrecen cada carrera y le recomienda instituciones —priorizando las de su ciudad— que cubren esos intereses.

Proyecto integrador desarrollado en **CESDE** con Spring Boot y PostgreSQL.

---

## 📚 Documentación interactiva de la API

| Herramienta | Local | Producción (Render) |
|---|---|---|
| **Swagger UI** | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | `https://<tu-servicio>.onrender.com/swagger-ui.html` |
| **Scalar** | [http://localhost:8080/scalar](http://localhost:8080/scalar) | `https://<tu-servicio>.onrender.com/scalar` |
| **OpenAPI (JSON)** | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) | `https://<tu-servicio>.onrender.com/v3/api-docs` |

> Reemplaza `<tu-servicio>` por el nombre real de tu servicio desplegado.

---

## 🎯 Idea de negocio

El problema: muchos estudiantes no saben qué carreras existen, qué habilidades requieren ni qué universidades las ofrecen cerca de ellos.

SIDCU resuelve esto con tres ideas:

1. **Catálogo**: carreras (con su nivel académico y habilidades que desarrollan) y universidades (con su ubicación).
2. **Oferta académica**: qué universidad ofrece qué carrera.
3. **Intereses y recomendación**: el estudiante marca carreras y universidades de interés, y el sistema recomienda universidades coherentes con esos intereses.

### Reglas de negocio implementadas

| Regla | Dónde |
|---|---|
| Un estudiante puede registrar **máximo 5 carreras de interés**, sin repetir. | `InteresService` |
| Solo se puede mostrar interés en una **universidad que ofrezca al menos una de las carreras de interés** del estudiante. | `InteresUniversidadService` |
| Una universidad **no puede ofrecer dos veces** la misma carrera. | `UniversidadCarreraService` |
| Un estudiante solo puede tener **un perfil** (relación 1:1). | `PerfilEstudianteService` |
| **Documento, nombre de carrera, de universidad y de habilidad son únicos.** | Servicios de cada recurso |
| **No se elimina** una carrera ofertada o con estudiantes interesados, ni una universidad con oferta o interesados, ni una habilidad asociada a carreras. | Servicios de cada recurso |
| **Recomendación**: universidades que ofrecen alguna carrera de interés del estudiante; primero las de su ciudad. | `EstudianteService` |

Las violaciones de reglas se responden con **HTTP 409** (`ReglaDeNegocioException`); los recursos inexistentes con **HTTP 404** (`RecursoNoEncontradoException`).

---

## 🧱 Modelo de datos

```
estudiantes 1 ──── 1 perfiles_estudiante
estudiantes 1 ──── N intereses ──────── N carreras
estudiantes 1 ──── N interes_universidad ─ N universidades
universidades 1 ── N universidad_carrera ─ N carreras
carreras N ──────── N habilidades          (tabla carrera_habilidad)
```

* **BaseEntity** (`@MappedSuperclass`): `id` autogenerado, `fecha_creacion`, `fecha_actualizacion`, `estado_activo`.
* **Embeddable**: `Ubicacion` (dirección y código postal de la universidad) y las claves compuestas `InteresId`, `InteresUniversidadId`, `UniversidadCarreraId`.
* **Enum**: `NivelAcademico` (`PREGRADO`, `POSTGRADO`, `TECNICO`, `TECNOLOGICO`), persistido con `@Enumerated(EnumType.STRING)`.
* Los **4 tipos de relación** JPA están presentes: `@OneToOne` (Estudiante–Perfil), `@OneToMany` (Estudiante–Intereses), `@ManyToOne` (Interés–Estudiante/Carrera) y `@ManyToMany` (Carrera–Habilidad).

---

## 🏗️ Arquitectura por capas

```
com.cesde.sidcudb
├── config/        CorsConfig, OpenApiConfig, DataSeederConfig
├── controller/    Reciben la petición, invocan al servicio y responden (sin lógica)
├── dto/
│   ├── request/   *RequestDTO  → solo entrada, con Bean Validation, sin IDs propios ni auditoría
│   └── response/  *ResponseDTO → solo salida, sin campos internos
├── exception/     RecursoNoEncontradoException, ReglaDeNegocioException, GlobalExceptionHandler
├── mapper/        Conversión entidad ⇄ DTO
├── model/
│   ├── base/      BaseEntity
│   ├── embeddable/
│   ├── entity/
│   └── enums/
├── repository/    Interfaces que extienden JpaRepository
└── service/       Reglas de negocio y manejo de excepciones
```

---

## 🔌 Endpoints principales

Todas las rutas están en minúsculas, con sustantivos en plural y sin verbos.

| Recurso | Ruta base | Operaciones |
|---|---|---|
| Carreras | `/api/carreras` | GET (`?area=&nivel=`), GET `/{id}`, POST, PUT `/{id}`, DELETE `/{id}` |
| Habilidades | `/api/habilidades` | CRUD |
| Universidades | `/api/universidades` | CRUD (`?ciudad=&nombre=`) |
| Estudiantes | `/api/estudiantes` | CRUD (`?documento=&ciudad=`) |
| Recomendaciones | `/api/estudiantes/{id}/universidades-recomendadas` | GET |
| Perfiles | `/api/perfiles-estudiantes` | CRUD (`?estudianteId=`) |
| Intereses en carreras | `/api/intereses` | GET, POST, GET/DELETE `/estudiantes/{estudianteId}/carreras/{carreraId}` |
| Intereses en universidades | `/api/intereses-universidades` | GET, POST, GET/DELETE `/estudiantes/{estudianteId}/universidades/{universidadId}` |
| Oferta académica | `/api/universidades-carreras` | GET, POST, GET/DELETE `/universidades/{universidadId}/carreras/{carreraId}` |

El detalle completo (esquemas, códigos de respuesta, pruebas en vivo) está en Swagger UI y Scalar.

**Formato de error uniforme:**

```json
{
  "timestamp": "2026-11-07T10:15:30",
  "status": 409,
  "error": "Conflict",
  "mensaje": "Un estudiante no puede registrar más de 5 carreras de interés",
  "ruta": "/api/intereses",
  "detalles": []
}
```

---

## 🛠️ Tecnologías

* Java 17 · Spring Boot 4 (Web MVC, Data JPA, Validation, Actuator)
* PostgreSQL
* Lombok
* springdoc-openapi (Swagger UI) y Scalar API Reference
* Maven · Docker

---

## ▶️ Ejecución local

**Requisitos:** JDK 17+, PostgreSQL y (opcional) Docker.

1. **Crear la base de datos**

   ```sql
   CREATE DATABASE sidcudb;
   ```

2. **Configurar variables de entorno** a partir de la plantilla:

   ```bash
   cp .env.example .env
   # edita .env con tus credenciales
   ```

   | Variable | Obligatoria | Descripción |
   |---|---|---|
   | `DB_URL` | ✅ | URL JDBC de PostgreSQL |
   | `DB_USERNAME` | ✅ | Usuario de la base de datos |
   | `DB_PASSWORD` | ✅ | Contraseña de la base de datos |
   | `PORT` | ❌ | Puerto HTTP (por defecto `8080`) |
   | `CORS_ALLOWED_ORIGINS` | ❌ | Orígenes permitidos, separados por comas (por defecto `*`) |
   | `DDL_AUTO` | ❌ | Estrategia de Hibernate (por defecto `update`) |
   | `SEED_ENABLED` | ❌ | Cargar datos de ejemplo si la BD está vacía (por defecto `true`) |

3. **Exportar las variables y ejecutar**

   ```bash
   # Linux / macOS
   export $(grep -v '^#' .env | xargs)
   ./mvnw spring-boot:run

   # Windows (PowerShell)
   Get-Content .env | Where-Object { $_ -notmatch '^#' -and $_ -match '=' } | ForEach-Object {
     $k,$v = $_ -split '=',2; [Environment]::SetEnvironmentVariable($k,$v)
   }
   .\mvnw.cmd spring-boot:run
   ```

4. Abrir <http://localhost:8080/swagger-ui.html> o <http://localhost:8080/scalar>.

Al primer arranque, si la base de datos está vacía, `DataSeederConfig` carga universidades, carreras, habilidades y estudiantes de ejemplo (Medellín y el Valle de Aburrá).

---

## 🐳 Docker

El `Dockerfile` usa una **construcción multietapa** (Maven + OpenJDK 17 para compilar, JRE liviano para ejecutar) y toma el puerto de la variable `PORT`.

```bash
docker build -t sidcu-backend .

docker run --rm -p 8080:8080 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/sidcudb \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=tu_contrasena \
  sidcu-backend
```

El archivo `.dockerignore` excluye `target/`, `.git`, `.env` y archivos de IDE para una imagen pequeña y sin secretos.

### Despliegue en Render

1. Crear un servicio **Web Service** apuntando a este repositorio con entorno **Docker**.
2. Crear una base **PostgreSQL** en Render y copiar su URL interna en formato JDBC.
3. Definir `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` en *Environment*. Render inyecta `PORT` automáticamente.

---

## ✅ Pruebas

```bash
./mvnw test
```

> `SidcudbApplicationTests` levanta el contexto completo, por lo que requiere las variables `DB_URL`, `DB_USERNAME` y `DB_PASSWORD` apuntando a una base disponible.
