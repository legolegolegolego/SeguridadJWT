
# Análisis en detalle: RsaKeyProperties.java

## Código:
```java
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}
```

---

## 1. Anotación `@ConfigurationProperties(prefix = "rsa")`
- **Propósito:**  
  Permite que esta clase mapee las propiedades de configuración definidas en el archivo `application.properties` o `application.yml`.  
  En este caso, todas las propiedades que tienen el prefijo `rsa` serán asignadas automáticamente a los atributos de la clase `RsaKeyProperties`.

- **Funcionamiento:**
  - Spring Boot buscará las propiedades que comienzan con `rsa` en los archivos de configuración.
  - Las propiedades `rsa.private-key` y `rsa.public-key` serán asignadas a los atributos `privateKey` y `publicKey`, respectivamente.
  - Spring utiliza los convertidores necesarios para transformar las claves definidas como rutas (en este caso, archivos PEM) en objetos del tipo `RSAPrivateKey` y `RSAPublicKey`.

---

## 2. Declaración de la clase como `record`
- **¿Qué es una `record`?**  
  - Introducida en Java 14 (como característica previa) y oficialmente en Java 16, una `record` es una forma concisa de declarar una clase inmutable.
  - Genera automáticamente:
    - Constructor.
    - Métodos `equals`, `hashCode` y `toString`.

- **En este caso:**  
  La clase `RsaKeyProperties` es inmutable y almacena dos campos:
  - **`RSAPublicKey publicKey`:** Clave pública RSA utilizada para verificar la firma de los tokens JWT.
  - **`RSAPrivateKey privateKey`:** Clave privada RSA utilizada para firmar los tokens JWT.

---

## 3. Relación con el archivo `application.properties`
El archivo de configuración define las propiedades para las claves públicas y privadas:

```properties
# Carga la clave privada y la clave pública en el contexto
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem
```

- **`rsa.private-key`:**  
  - Especifica la ruta del archivo PEM que contiene la clave privada.
  - `classpath:` indica que el archivo se encuentra en el directorio `resources/certs` del proyecto.

- **`rsa.public-key`:**  
  - Especifica la ruta del archivo PEM que contiene la clave pública.

- **Cómo Spring maneja estas propiedades:**
  - Spring Boot utiliza `@ConfigurationProperties` para leer estas rutas y, con ayuda de convertidores predeterminados o personalizados, transforma los datos en objetos `RSAPrivateKey` y `RSAPublicKey`.

---

## 4. Contexto de uso en una API REST segura
En una API REST que utiliza JWT (JSON Web Tokens) para la autenticación:
- **Firma de tokens JWT:**  
  - Se utiliza la clave privada (`RSAPrivateKey`) para firmar los tokens. Esto garantiza que los tokens no puedan ser manipulados por el cliente.

- **Verificación de tokens JWT:**  
  - La clave pública (`RSAPublicKey`) se utiliza para verificar que los tokens fueron firmados con la clave privada correspondiente.

---

## 5. Ventajas de usar esta clase
1. **Centralización de la configuración:**  
   Las claves RSA son gestionadas desde un solo lugar, haciendo el código más limpio y menos propenso a errores.

2. **Inmutabilidad:**  
   Al ser un `record`, los valores de las claves no pueden ser modificados después de ser cargados. Esto es crucial para la seguridad.

3. **Soporte para entornos externos:**  
   La clase permite cargar claves desde diferentes ubicaciones (por ejemplo, variables de entorno o archivos externos).

---

## Cómo Spring utiliza esta clase
1. **Carga inicial:**
   - Spring Boot detecta la anotación `@ConfigurationProperties` y busca las propiedades con el prefijo `rsa`.
   - Usa un bean de tipo `RsaKeyProperties` para almacenar las claves cargadas.

2. **Uso en seguridad JWT:**  
   Estas claves se pasan a otros componentes (como un `TokenService`) para:
   - Firmar tokens con la clave privada.
   - Verificar tokens con la clave pública.

---

---

## Resumen de la funcionalidad
La clase **`RsaKeyProperties`**:
1. Mapea las propiedades `rsa.private-key` y `rsa.public-key` desde el archivo `application.properties`.
2. Proporciona un contenedor seguro e inmutable para las claves RSA utilizadas en seguridad JWT.
3. Integra claves RSA con otros componentes de Spring Boot, asegurando que las claves sean accesibles en toda la aplicación.
