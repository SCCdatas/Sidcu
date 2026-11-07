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
DB_URL=jdbc:postgresql://ep-icy-base-be1eo1as-pooler.c-9.us-east-2.aws.neon.tech/neondb?sslmode=require
DB_USERNAME=neondb_owner
DB_PASSWORD=npg_9sYFDbgPI7ik
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

(Opcional) Variable `VITE_API_URL` = URL del backend.

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
