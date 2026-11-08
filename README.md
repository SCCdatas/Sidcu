# SIDCU – Simulador Integral de Decisiones de Carrera Universitaria

Monorepo del proyecto SIDCU (CESDE).

```
sidcu/
├── backend/     → Spring Boot  + PostgreSQL (Neon) + Dockerfile
└── frontend/    → React + Vite
```

## Despliegue en Render (monorepo)

### 1. Backend – Web Service (Docker)

| Campo | Valor |
|-------|--------|
| Root Directory | `backend` |
| Runtime | Docker |
| Dockerfile Path | `./Dockerfile` |

**Environment variables:**

```
DB_URL=jdbc:postgresql://<host>:5432/<database>?sslmode=require
DB_USERNAME=<database-user>
DB_PASSWORD=<configure-in-Render>
CORS_ALLOWED_ORIGINS=*
DDL_AUTO=update
SEED_ENABLED=false
```

- Health: `https://<tu-backend>.onrender.com/actuator/health`
- Swagger: `https://<tu-backend>.onrender.com/swagger-ui.html`

### 2. Frontend – Static Site

| Campo | Valor |
|-------|--------|
| Root Directory | `frontend` |
| Build Command | `npm install && npm run build` |
| Publish Directory | `dist` |

El catálogo de carreras y universidades consulta el backend (`/api/carreras` y
`/api/universidades`). El frontend usa `https://sidcu-2.onrender.com` por defecto.
Si el backend cambia de URL, configura `VITE_API_URL` en el Static Site de Render
y vuelve a desplegarlo. El backend debe permitir el origen del frontend en
`CORS_ALLOWED_ORIGINS`.

## Desarrollo local

### Backend
```bash
cd backend
cp .env.example .env
# Edita .env si hace falta
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Base de datos

PostgreSQL en **Neon**. Ya contiene:
- Tablas del modelo
- 40 estudiantes de prueba
- Carreras, universidades, habilidades y relaciones

No actives el seeder (`SEED_ENABLED=false`) si los datos ya están cargados.
