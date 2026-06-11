#### 📦 Phase 1: Robustness, Persistencia y Trazabilidad (Completada)
* **RF-1.1: Operaciones CRUD Completes:** El sistema debe permitir enlistar (`GET`), buscar por ID (`GET`), crear (`POST`), actualizar totalmente (`PUT`), actualizar parcialmente (`PATCH`) y eliminar (`DELETE`) productos.
* **RF-1.2: Inicialización de Datos:** El sistema debe pre-cargar la base de datos con un set de datos de prueba (`data.sql`) de forma transparente al arrancar.
* **RF-1.3: Control de Errores Semántico:** El sistema debe interceptar excepciones mediante un `@ControllerAdvice` global y devolver respuestas estandarizadas en formato JSON con códigos HTTP adecuados (ej. `404 Not Found` para recursos inexistentes, `503 Service Unavailable` para fallos de infraestructura).
* **RF-1.4: Políticas de Reintento con AOP:** Las consultas de lectura deben contar con un mecanismo de reintento automático con backoff exponencial ante intermitencias de conexión a la base de datos. Implementado mediante una anotación personalizada (`@DatabaseRetry`) restringida a métodos seguros.
* **RF-1.5: Persistencia de Logs:** Las operaciones clave del sistema y los errores deben registrarse con diferentes niveles de criticidad (`INFO`, `WARN`, `ERROR`) mediante `@Slf4j` y guardarse automáticamente en un archivo físico rotativo (`logs/app.log`).

### 🔐 Phase 2: Seguridad Perimetral y Control de Acceso (En Desarrollo)
* **RF-2.1: Autenticación Stateless (Sin Estado):** El sistema no debe almacenar sesiones en el servidor; debe validar la identidad del cliente mediante tokens criptográficos en cada petición HTTP.
* **RF-2.2: Generación Criptográfica de Tokens:** El sistema debe emitir tokens JWT firmados con el algoritmo HS256, incluyendo el nombre de usuario, roles de acceso y una expiración estricta de 30 minutos.
* **RF-2.3: Gestión de Credenciales Segura:** Las contraseñas de los usuarios deben almacenarse en la base de datos aplicando un hash seguro mediante el algoritmo **BCrypt**. Queda prohibido el almacenamiento en texto plano.
* **RF-2.4: Filtro Interceptor Integrado:** Cada petición dirigida a las rutas protegidas (`/api/products/**`) debe pasar por un filtro personalizado (`OncePerRequestFilter`) para extraer y validar el token `Bearer` provisto en la cabecera `Authorization`.
* **RF-2.5: Control de Acceso Basado en Roles (RBAC):** * Cualquier usuario autenticado con rol `USER` o `ADMIN` puede consultar productos (`GET`).
  * Solo los usuarios con rol `ADMIN` pueden alterar el inventario (`POST`, `PUT`, `PATCH`, `DELETE`).

### 🐳 Phase 3: Contenedores e Infraestructura de Producción (Próxima)
* **RF-3.1: Dockerización Multifase (Multi-Stage Build):** La aplicación debe empaquetarse en una imagen Docker optimizada utilizando un entorno de compilación (Maven) y un entorno de ejecución (JRE ligero) independientes para minimizar la superficie de ataque y el tamaño final.
* **RF-3.2: Migración a Base de Datos Relacional Real:** El sistema debe transicionar de la base de datos H2 en memoria hacia una instancia aislada y persistente de **PostgreSQL**.
* **RF-3.3: Orquestación Local Completa:** Se debe proveer un archivo `docker-compose.yml` para levantar todo el ecosistema (Aplicación Spring Boot + Servidor PostgreSQL + Volúmenes de datos mapeados) con un solo comando.

### 🚀 Phase 4: Arquitectura Distribuida de Microservicios (Final)
* **RF-4.1: Desacoplamiento de Dominios:** El monolito debe dividirse en dos procesos completamente independientes y con bases de datos aisladas: **Microservicio de Productos** y **Microservicio de Órdenes de Compra**.
* **RF-4.2: Servidor de Descubrimiento (Service Discovery):** Implementación de un servidor **Netflix Eureka** que funcionará como el directorio dinámico donde cada microservicio se registrará autónomamente al iniciar.
* **RF-4.3: Puerta de Entrada Única (API Gateway):** Un componente **Spring Cloud Gateway** centralizará el tráfico exterior, validará la seguridad global y redistribuirá las peticiones internamente mediante el patrón *Token Relay*.
* **RF-4.4: Tolerancia a Fallos en Cascada (Circuit Breaker):** El microservicio de Órdenes debe implementar **Resilience4j** para interceptar caídas del servicio de productos, aislando el error y respondiendo con un comportamiento alternativo (*Fallback*) sin degradar el sistema completo.

---

## 3. Non-Functional Requirements (NFR)

* **RNF-3.1: Rendimiento en Trazabilidad:** El sistema de logging no debe bloquear los hilos de ejecución de las peticiones HTTP concurrentes (`SLF4J` asíncrono sobre Logback).
* **RNF-3.2: Seguridad de la Información:** Queda estrictamente prohibido exponer secretos de firma de tokens (JWT Secret) o credenciales de producción en el código fuente. Toda configuración sensible debe inyectarse en tiempo de ejecución a través de variables de entorno.
* **RNF-3.3: Idempotencia en Resiliencia:** La política de reintentos automáticos configurada en `@DatabaseRetry` solo puede ser aplicada a operaciones idempotentes (`GET`). Los métodos de escritura (`POST`, `PUT`, `PATCH`) quedan excluidos para prevenir duplicidad de datos accidentales por fallos de red.
* **RNF-3.4: Portabilidad Absoluta:** La suite completa debe ser capaz de compilarse y ejecutarse de manera idéntica en cualquier sistema operativo (Linux, macOS, Windows) mediante Docker Engine, eliminando la dependencia del JDK local del host.

---

## 4. Technical Stack Summary

| Component | Technology | Version | Scope / Responsibility |
| :--- | :--- | :--- | :--- |
| **Core Framework** | Spring Boot | 4.x / 3.x Latest | REST API, Dependency Injection, Core Logic |
| **Security** | Spring Security / JJWT | 0.12.5 | Stateless Authentication, Filter Chain, RBAC |
| **Resiliency** | Spring Retry / Aspects | 2.0.6 | Exponential Backoff, Custom Annotations (AOP) |
| **Database (Dev)** | H2 Database | Embedded | Rapid prototyping, automatic seeding (`data.sql`) |
| **Database (Prod)**| PostgreSQL | 16-alpine | Production-ready persistence |
| **Dev Tools** | Project Lombok | - | Boilerplate reduction (`@Data`, `@Slf4j`) |
| **Containerization**| Docker / Compose | 3.8+ | Multi-stage image build & infrastructure orchestration |

---

## 5. Current Phase (Phase 2.1) Acceptance Criteria
Para dar por aprobada la sub-fase actual del desarrollo de seguridad, la aplicación debe cumplir con éxito las siguientes condiciones:
1. **Filtro Restrictivo:** Cualquier petición a `GET /api/products` sin una cabecera `Authorization: Bearer <token>` válida debe ser rechazada de inmediato con un código de estado `401 Unauthorized`.
2. **Componente de Extracción:** El `JwtService` debe ser capaz de procesar un token firmado con la clave del archivo `application.properties`, verificar su vigencia y extraer el *username* sin lanzar excepciones.
3. **Manejo Controlado de Firmas:** Un token alterado, corrupto o cuya fecha de expiración haya vencido debe disparar una excepción interceptada por el `GlobalExceptionHandler`, respondiendo con una estructura estructurada amigable al cliente en lugar de una traza interna expuesta.
"""

with open("PRODUCT_REQUIREMENT_DOCUMENT.md", "w", encoding="utf-8") as f:
    f.write(prd_content)
