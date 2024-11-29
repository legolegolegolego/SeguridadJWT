
# Análisis en detalle: SecurityConfig.java

## Código:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/login", "/usuarios/register").permitAll()
                        .requestMatchers("/ruta_protegida/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
```

---

## 1. Anotaciones de clase
- **`@Configuration`**  
  Marca la clase como una clase de configuración, que define beans gestionados por Spring.

- **`@EnableWebSecurity`**  
  Activa la configuración de seguridad de Spring Security para la aplicación.

---

## 2. Métodos principales

### **2.1. Método `securityFilterChain(HttpSecurity http)`**
Configura los filtros de seguridad para la aplicación:

- **Deshabilitación de CSRF:**  
  `http.csrf(csrf -> csrf.disable())` desactiva la protección contra CSRF, ya que las APIs REST usan tokens JWT.

- **Reglas de autorización:**
  - `/usuarios/login` y `/usuarios/register`: Permiten acceso sin autenticación.
  - `/ruta_protegida/**`: Requieren autenticación.
  - Otras rutas: Todas deben estar autenticadas.

- **Autenticación con JWT:**  
  Configura el servidor de recursos para usar JWT (`oauth2ResourceServer(oauth2 -> oauth2.jwt(...))`).

- **Sin sesiones:**  
  `sessionManagement(...).sessionCreationPolicy(SessionCreationPolicy.STATELESS)` asegura que no se utilicen sesiones, haciendo que cada solicitud sea independiente.

---

### **2.2. Método `passwordEncoder()`**
Proporciona un bean para la codificación segura de contraseñas con BCrypt:

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

### **2.3. Método `authenticationManager(AuthenticationConfiguration authenticationConfiguration)`**
Proporciona un manejador central de autenticaciones:

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}
```

---

### **2.4. Método `jwtDecoder()`**
Configura la decodificación de tokens JWT utilizando la clave pública RSA:

```java
@Bean
public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
}
```

---

### **2.5. Método `jwtEncoder()`**
Configura la codificación de tokens JWT utilizando las claves RSA (pública y privada):

```java
@Bean
public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
}
```

---

## Resumen de la funcionalidad
La clase **`SecurityConfig`**:
1. Configura la autenticación y autorización mediante filtros.
2. Define el uso de JWT para proteger y verificar las solicitudes.
3. Establece una arquitectura sin estado (stateless).
4. Expone beans como `PasswordEncoder`, `JwtEncoder`, y `JwtDecoder` para su uso en otros componentes.

