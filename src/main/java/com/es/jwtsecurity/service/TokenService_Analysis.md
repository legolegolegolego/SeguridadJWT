
# Análisis en detalle: TokenService.java

## Código:
```java
@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("roles", roles)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
```

---

## 1. Anotaciones de clase

- **`@Service`**  
  Marca la clase como un servicio gestionado por Spring. Es un componente que puede ser inyectado en otras partes de la aplicación.

---

## 2. Atributos de la clase

### **`JwtEncoder jwtEncoder`**
- **Propósito:**  
  Responsable de codificar y firmar tokens JWT.
- **Inyección automática:**  
  Spring Security inyecta este bean que utiliza las claves RSA configuradas para firmar tokens.

---

## 3. Método principal: `generateToken`

### **Propósito**
Genera un token JWT que contiene información del usuario autenticado, incluyendo roles, fechas de emisión y expiración.

### **Parámetro**
- **`Authentication authentication`**  
  Proporciona información del usuario autenticado, como:
  - Nombre de usuario.
  - Roles o permisos.
  - Credenciales.

### **Retorno**
- **`String`**  
  Devuelve el token JWT generado en formato estándar: `HEADER.PAYLOAD.SIGNATURE`.

---

### **Detalles del proceso**

#### **1. Obtener la hora actual**
```java
Instant now = Instant.now();
```
Obtiene la hora actual en formato UTC para utilizarla en las fechas de emisión y expiración del token.

---

#### **2. Extraer roles del usuario**
```java
String roles = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
```
- Extrae los roles del usuario autenticado (`GrantedAuthority`) y los convierte en una cadena separada por espacios.

---

#### **3. Construir el conjunto de reclamos**
```java
JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("roles", roles)
        .build();
```
- **`JwtClaimsSet`**: Define los datos que estarán en el payload del token.
- **Campos principales:**
  - **`issuer("self")`**: Define quién emite el token.
  - **`issuedAt(now)`**: Fecha de emisión.
  - **`expiresAt(now.plus(1, ChronoUnit.HOURS))`**: Fecha de expiración (1 hora desde la emisión).
  - **`subject(authentication.getName())`**: El nombre del usuario autenticado.
  - **`claim("roles", roles)`**: Incluye los roles del usuario como un reclamo adicional.

---

#### **4. Codificar y firmar el token**
```java
return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
```
- **`jwtEncoder.encode`**: Firma el token utilizando la clave privada RSA.
- **`JwtEncoderParameters.from(claims)`**: Proporciona los reclamos al codificador.
- **`getTokenValue()`**: Devuelve el token generado.

---

## 4. Resumen de la funcionalidad

La clase **`TokenService`**:
1. Genera tokens JWT firmados con claves RSA.
2. Incluye información relevante del usuario en el payload del token (nombre, roles, fechas).
3. Utiliza `JwtEncoder` para asegurar la integridad y autenticidad del token.

---

## 5. Flujo del método `generateToken`

1. Obtiene la hora actual (`Instant.now()`).
2. Extrae los roles del usuario autenticado.
3. Construye los reclamos (`JwtClaimsSet`) que se incluirán en el token.
4. Firma y codifica el token utilizando `JwtEncoder`.
5. Devuelve el token JWT como un `String`.

---

## 6. Conexión con el sistema

- **Login:**  
  Este método es utilizado durante el proceso de autenticación para generar un token JWT tras validar las credenciales del usuario.

- **Seguridad basada en tokens:**  
  El token generado se utiliza para autenticar solicitudes posteriores a través de encabezados HTTP.

