# API Stock Manager - Backend (Spring Boot)

Este repositorio contiene la API REST para el manejo de usuarios y productos. Está construida con Spring Boot, Java y MySQL, implementando un diseño desacoplado y un estricto control de trazabilidad.

## 🛠️ Características Implementadas

* **Autenticación y RBAC:** Seguridad basada en JWT con tiempo de expiración corto. Manejo de roles estricto (`ROLE_ADMIN` y `ROLE_ANONYNOUS`).
* **Endpoints Protegidos (/products):** Los métodos `POST`, `PUT` y `DELETE` están restringidos exclusivamente para el rol de administrador mediante `@PreAuthorize("hasRole('ADMIN')")`. El acceso de lectura (`GET`) es 100% público.
* **Bitácora de Auditoría Desacoplada:** Registro histórico de cambios (CREATE, UPDATE, DELETE) hechos por administradores, implementado de forma desacoplada mediante eventos/observadores.
* **Trazabilidad de Peticiones:** Filtro global que intercepta la cabecera `X-Correlation-ID`, la propaga en todos los logs de la consola.
* **Manejo de Errores y CORS:** Respuestas de error estandarizadas en JSON y configuración de CORS explícita para permitir peticiones desde `http://localhost:4200`.

---

## 🚀 Desarrollo Local (Sin Docker)

Si deseas correr la API de forma independiente fuera del contenedor, asegúrate de tener una instancia de MySQL corriendo y configura las credenciales de el `application.properties`.

Ejecuta la aplicación:
   ```bash
   ./mvn spring-boot:run
   ```

La API levantará por defecto en el puerto **`http://localhost:8080`**.

---

## 📦 Despliegue en Producción

Para levantar este servicio junto con la base de datos MySQL y la interfaz de usuario, utiliza el repositorio del orquestador:

🔗 **[Stock Manager Orchestrator](https://github.com/PetterTorrez/stock-manager-orchestrator)**
