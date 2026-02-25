# Seismap Migration — Checklist Detallado

## Phase 0: Decisiones ✅
- [x] Mapping library → **OpenLayers**
- [x] GeoServer → **Docker service** (`kartoza/geoserver`)
- [x] Auth → **Diferida** (usuario hardcodeado en V1)
- [x] Admin data-load → **Incluido** en V1
- [x] UI library → **MUI (Material UI)**

---

## Phase 1: Project Scaffolding

### 1.1 Backend (`seismap-backend/`) ✅
- [x] Generar proyecto Spring Boot 3.4 con Maven (web, data-jpa, postgresql, flyway, validation)
- [x] Configurar `pom.xml` con dependencias adicionales (hibernate-spatial, jackson-datatype-jts, springdoc-openapi)
- [x] Crear estructura de paquetes: `controller`, `service`, `repository`, `model/entity`, `dto`, `config`
- [x] Configurar `application.yml` con datasource placeholder para PostgreSQL/PostGIS

### 1.2 Frontend (`seismap-frontend/`) ✅
- [x] Crear proyecto React + TypeScript con Vite
- [x] Instalar dependencias: MUI, OpenLayers (`ol`), React Router, Axios
- [x] Crear estructura de carpetas: `components/`, `pages/`, `services/`, `hooks/`, `types/`
- [x] Configurar proxy de desarrollo en `vite.config.ts` para apuntar al backend

### 1.3 Docker Compose ✅
- [x] Crear `docker-compose.yml` con servicios: `postgres`, `geoserver`, `backend`, `frontend`
- [x] Configurar volúmenes para datos de PostgreSQL y GeoServer
- [x] Configurar red interna entre servicios
- [x] Verificar que `docker-compose up` levanta postgres y geoserver correctamente

---

## Phase 2: Database & Persistence Layer ✅

### 2.1 Schema y Migraciones ✅
- [x] Extraer esquema SQL del código legacy (entidades Hibernate)
- [x] Crear migración Flyway `V1__init_postgis.sql`
- [x] Incluir extensión PostGIS (`CREATE EXTENSION IF NOT EXISTS postgis`)

### 2.2 Entidades JPA ✅
- [x] `Application` + `ApplicationSettings`
- [x] `Agency`
- [x] `Category`
- [x] `Event` (con campo geométrico `Point` para location)
- [x] `EventAndAverageMagnitudes`
- [x] `EventInfo`
- [x] `Magnitude`
- [x] `MagnitudeDataBounds`
- [x] `MagnitudeLimits`
- [x] `SeismapMap` (configuración de mapa del usuario)
- [x] `Style`
- [x] `DataBounds`
- [x] `User`

### 2.3 Repositories ✅
- [x] Crear Spring Data JPA repositories para cada entidad (10 repos)
- [x] Verificar compilación Maven ✅

---

## Phase 3: Backend REST APIs ✅

### 3.1 Application & Config ✅
- [x] `GET /api/application`
- [x] `GET /api/application/settings`

### 3.2 Events ✅
- [x] `GET /api/events/{id}`
- [x] `PUT /api/events/{id}`
- [x] `GET /api/events/data-bounds`
- [x] `GET /api/events/magnitude-limits`

### 3.3 Maps ✅
- [x] `GET /api/maps/default`
- [x] `POST /api/maps`
- [x] `PATCH /api/maps/{id}/name`
- [x] `DELETE /api/maps/{id}`
- [x] `PUT /api/maps/{id}`
- [x] `GET /api/maps/{id}`
- [x] `GET /api/maps?userId={id}`
- [x] `GET /api/maps/legend?name={sld}` (Phase 6, requiere GeoServer)

### 3.4 Styles ✅
- [x] `POST /api/styles`
- [x] `GET /api/styles`

### 3.5 Categories ✅
- [x] `POST /api/categories`
- [x] `GET /api/categories`

### 3.6 Admin (Data Loading) ✅
- [x] `GET /api/admin/data-files`
- [x] `POST /api/admin/load-data-file` (stub — parser a portar)

### 3.7 GeoServer Proxy
- [ ] Configurar Nginx para proxy a GeoServer (Phase 6)

### 3.8 Documentación ✅
- [x] springdoc-openapi en pom.xml + application.yml

### Compilación ✅
- [x] `mvn compile` exitoso con `JAVA_HOME=~/.sdkman/candidates/java/21.0.2-open`

---

## Phase 4: Frontend Base Setup ✅
- [x] Crear layout principal (`MainLayout`: sidebar colapsable + área de mapa)
- [x] Inicializar componente OpenLayers con capas base OSM (`SeismapMapView.tsx`)
- [x] Configurar tema MUI dark (navy + azul sísmico)
- [x] Configurar React Router (`/` → mapa, `/admin` → admin)
- [x] Crear servicio HTTP base con Axios + servicios por dominio (`seismap.ts`)
- [x] Tipos TypeScript para todas las entidades (`types/map.ts`)
- [x] `npm run build` exitoso (2.8s, sin errores TS)

---

## Phase 5: Frontend Features ✅

### 5.1 Mapa Principal ✅
- [x] Panel de parámetros (`MapControlsPanel`: filtros de fecha, profundidad, magnitud)
- [x] Controles de animación
- [x] Selector de estilos
- [x] Conexión con capas GeoServer (Phase 6)
- [x] Leyenda del mapa (Phase 6)

### 5.2 Gestión de Mapas ✅
- [x] Guardar mapa (botón en `SavedMapsPanel`)
- [x] Renombrar mapa (dialog inline)
- [x] Cargar mapa existente (lista clickeable)
- [x] Eliminar mapa

### 5.3 Eventos Sísmicos
- [x] Polígono de selección → lista de eventos (Verificado)
- [x] Diálogo de detalle de evento (Implementado)
- [x] Click en punto → evento específico (Verificado, solucionado WKB y Lat/Lon EPSG:3857)

### 5.4 Filtros Avanzados
- [x] Herramienta de polígono + mapa de profundidad (Filtros base operativos en MapControlsPanel)
- [x] Filtros por Fecha (Relativa y Absoluta)
- [x] Filtros por Magnitud
- [x] Filtros por Profundidad

### 5.5 Admin ✅
- [x] Página de listado de archivos `.data`
- [x] Botón de carga de archivo → llamada a API

### Build ✅
- [x] `mvn compile` exitoso con `JAVA_HOME=~/.sdkman/candidates/java/21.0.2-open`
- [x] `npm run build` exitoso (3.68s, sin errores TS)

---

## Phase 6: Docker & Delivery ✅

- [x] Dockerfile Backend (multi-stage Maven 3.9 + JRE 21 Alpine)
- [x] Dockerfile Frontend (Node 20 Alpine build + Nginx 1.27 Alpine)
- [x] `nginx.conf` (SPA pushState, proxy /api/ y /layerServer/, gzip, cache assets)
- [x] `docker-compose.yml` actualizado con los 4 servicios (health checks)
- [x] `spring-boot-starter-actuator` agregado para health checks Docker
- [x] Carpeta `data/` creada para archivos .data
- [x] `README.md` con instrucciones de setup, desarrollo local y variables de entorno
- [x] Test end-to-end: `docker compose up` (requiere build de imágenes)


