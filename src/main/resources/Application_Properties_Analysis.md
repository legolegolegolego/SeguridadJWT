
# Análisis en detalle: application.properties

El archivo `application.properties` contiene configuraciones clave para la aplicación Spring Boot. A continuación, se analiza cada sección:

---

## **1. Configuración general de la aplicación**

```properties
spring.application.name=JwtSecurity
```

- **`spring.application.name`**  
  Define el nombre de la aplicación. Este nombre es útil para identificar la aplicación en entornos distribuidos o sistemas de registro.

---

## **2. Configuración de JPA e Hibernate**

```properties
spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
spring.jpa.show-sql=true
```

- **`spring.jpa.hibernate.ddl-auto=create`**  
  Configura cómo Hibernate manejará el esquema de la base de datos:
  - `create`: Crea las tablas en cada inicio, eliminando las existentes.  
  - **Nota:** Útil para desarrollo, no se recomienda en producción.

- **`spring.jpa.properties.hibernate.globally_quoted_identifiers=true`**  
  Asegura que los nombres de tablas y columnas sean tratados como identificadores entre comillas dobles. Útil para evitar conflictos con palabras reservadas.

- **`spring.jpa.show-sql=true`**  
  Muestra las consultas SQL generadas por Hibernate en la consola para depuración.

---

## **3. Configuración del servidor**

```properties
server.port=8080
```

- **`server.port`**  
  Especifica el puerto donde el servidor embebido (Tomcat) escuchará las solicitudes HTTP. En este caso, la aplicación estará disponible en `http://localhost:8080`.

---

## **4. Configuración de la base de datos**

```properties
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_bd
spring.datasource.username=root
spring.datasource.password=
```

- **`spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect`**  
  Define el dialecto de Hibernate para consultas SQL específicas de MySQL.

- **`spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver`**  
  Especifica el driver JDBC para conectarse a MySQL.

- **`spring.datasource.url=jdbc:mysql://localhost:3306/jwt_bd`**  
  Define la URL de conexión:
  - `localhost`: Dirección del servidor de la base de datos.
  - `3306`: Puerto predeterminado de MySQL.
  - `jwt_bd`: Nombre de la base de datos.

- **`spring.datasource.username` y `spring.datasource.password`**  
  Credenciales para acceder a la base de datos:
  - `username=root`: Usuario con acceso a la base de datos.
  - `password=`: Contraseña asociada al usuario.

**Nota:** Dejar la contraseña en blanco no es seguro para producción. Se recomienda usar variables de entorno.

---

## **5. Configuración de claves RSA para seguridad JWT**

```properties
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem
```

- **`rsa.private-key` y `rsa.public-key`**  
  Especifican las ubicaciones de las claves RSA utilizadas para firmar y verificar tokens JWT:
  - `classpath:`: Los archivos están en el directorio `resources`.
  - `certs/private.pem`: Clave privada RSA.
  - `certs/public.pem`: Clave pública RSA.

- **Uso:**  
  - La clave privada firma los tokens JWT.
  - La clave pública verifica la autenticidad de los tokens.

---

## **6. Configuración para popular la base de datos**

```properties
spring.jpa.properties.javax.persistence.sql-load-script-source=sql/datos-prueba.sql
```

- **`spring.jpa.properties.javax.persistence.sql-load-script-source`**  
  Especifica un archivo SQL para llenar la base de datos con datos iniciales al iniciar la aplicación:
  - `sql/datos-prueba.sql`: Archivo ubicado en el directorio `resources/sql`.

**Nota:** Esto es útil para desarrollo y pruebas, pero no se recomienda en producción.

---

## **Resumen de la funcionalidad**

1. **Identificación de la aplicación:**  
   Configura el nombre de la aplicación como `JwtSecurity`.

2. **Base de datos:**  
   - Configura la conexión con MySQL.
   - Define el dialecto de Hibernate y cómo manejar el esquema.

3. **Servidor:**  
   Define el puerto del servidor (`8080`).

4. **Seguridad JWT:**  
   - Especifica las claves RSA para firmar y verificar tokens.

5. **Inicialización de datos:**  
   - Utiliza un script SQL para preparar datos iniciales.

---

## **Recomendaciones para producción**

- **Seguridad:**
  - Almacenar las credenciales de la base de datos en variables de entorno.
  - Usar claves RSA almacenadas en un gestor seguro de secretos.
  
- **Base de datos:**
  - Cambiar `spring.jpa.hibernate.ddl-auto=create` por `validate` o `none`.

- **Puerto del servidor:**
  - Configurar el puerto según el entorno (`8080` para desarrollo, otros para producción).
