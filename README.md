
# Enunciado del ejercicio: Creación de una API REST con gestión de usuarios, roles, autenticación y sesiones

## Descripción del ejercicio

El objetivo de este ejercicio es desarrollar una API REST en **Spring Boot** para gestionar usuarios, productos y sesiones. Implementarás autenticación manual, un sistema de roles con privilegios diferenciados, y el manejo de contraseñas hasheadas y sesiones con tokens cifrados.

---

## Estructura de las entidades

Se trabajará con las siguientes tres entidades:

1. **Usuario**:
   - `id` (Long): Identificador único del usuario.
   - `nombre` (String): Nombre del usuario (debe ser único).
   - `password` (String): Contraseña del usuario (debe estar hasheada).
   - `rol` (String): Rol del usuario, que puede ser `USER` o `ADMIN`.

2. **Producto**:
   - `id` (Long): Identificador único del producto.
   - `nombre` (String): Nombre del producto.
   - `stock` (int): Cantidad disponible del producto.
   - `precio` (boolean): Indicador de si el producto tiene un precio válido o no.

---

## Requisitos funcionales

1. **Gestión de Usuarios**:
   - Los usuarios deben estar almacenados en la base de datos con los atributos mencionados.
   - Las contraseñas deben estar **hasheadas** utilizando un algoritmo seguro como `BCrypt`.
   - Cada usuario debe tener un rol:
      - `USER`: Solo puede acceder al endpoint para obtener un producto.
      - `ADMIN`: Puede acceder a los endpoints para obtener y crear productos.

4. **Autenticación mediante JWT**:
   - Los usuarios deben autenticarse mediante el endpoint `POST /login`, enviando su nombre y contraseña.
   - Si las credenciales son válidas:
      - Se genera un **token** que debe enviarse al cliente para que lo almacene.


---

---

## Endpoints requeridos

1. **Autenticación**:
   - `POST /usuarios/login`: Permite al usuario autenticarse.
      - **RUTA PÚBLICA** Todas las peticiones a este endpoint deben permitirse
      - **Entrada**: JSON con `nombre` y `password`.
      - **Salida**: Token de la sesión si las credenciales son válidas.

2. **Registro**:
   - `POST /usuarios/register`: Permite al usuario registrarse.
      - **RUTA PÚBLICA** Todas las peticiones a este endpoint deben permitirse
      - **Entrada**: JSON con `nombre`, `password` y `roles`.
      - **Salida esperada**: JSON con el usuario registrado

3. **Info de Usuario**:
   - `GET /usuarios/{nombre}`: Permite al usuario consultar su información
      - **RUTA PROTEGIDA** Sólo los usuarios autenticados pueden acceder a este recurso.
      - Usuarios con rol ADMIN pueden acceder a este recurso
      - Usuarios con el mismo nombre que el que se consulta pueden acceder a este recurso
      - Usuarios con rol USER con nombre diferente a este recurso *NO* pueden acceder al mismo
      - **Salida esperada**: JSON con el usuario consultado

4. **Gestión de Productos**:
   - *RUTAS PROTEGIDAS* Todas las rutas requieren que el usuario esté autenticado para acceder a las mismas
   - `GET /productos/{id}`: Devuelve la información de un producto.
      - **Entrada**: ID del producto.
      - **Salida**: JSON con `nombre`, `stock`, y `precio`.
   - `GET /productos/byNombre/{nombre}`: Devuelve la información de un producto.
      - **Entrada**: nombre del producto.
      - **Salida**: JSON con `nombre`, `stock`, y `precio`.
   - `GET /productos/asc`: Devuelve todos los productos almacenados en la base de datos ordenados ascendentemente por su *nombre*
      - **Salida**: Lista con los productos : JSON con `nombre`, `stock`, y `precio`.
   - `GET /productos/desc`: Devuelve todos los productos almacenados en la base de datos ordenados descendentemente por su *nombre*
      - **Salida**: Lista con los productos : JSON con `nombre`, `stock`, y `precio`.
   - `POST /productos`: Permite insertar un nuevo producto.
      - *SÓLO ADMIN*: Sólo los usuarios con ROL ADMIN pueden acceder a este recurso
      - **Entrada**: JSON con `nombre`, `stock`, y `precio`.
   - `DELETE /productos`: Permite insertar un nuevo producto.
      - *SÓLO ADMIN*: Sólo los usuarios con ROL ADMIN pueden acceder a este recurso
      - **Entrada**: JSON con `nombre`, `stock`, y `precio`.

---

## Criterios de evaluación

1. **Funcionalidad**:
   - La API debe cumplir con todos los requisitos especificados.
2. **Seguridad**:
   - Las contraseñas deben manejarse de forma segura.
3. **Organización**:
   - Estructura limpia y modular del proyecto.
   - Código bien documentado.
4. **Pruebas**:
   - Incluye pruebas unitarias para los servicios y pruebas de integración para los endpoints.

---

**¡Manos a la obra!**
