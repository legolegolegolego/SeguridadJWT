
# Análisis en detalle: UsuarioController.java

## Código:
```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword())
            );
        } catch (Exception e) {
            System.out.println("Excepcion en authentication");
            e.printStackTrace();
        }

        String token = "";
        try {
            token = tokenService.generateToken(authentication);
        } catch (Exception e) {
            System.out.println("Excepcion en generar token");
            e.printStackTrace();
        }

        return token;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioRegisterDTO> register(
            @RequestBody UsuarioRegisterDTO usuarioRegisterDTO) {
        customUserDetailsService.registerUser(usuarioRegisterDTO);
        return new ResponseEntity<UsuarioRegisterDTO>(usuarioRegisterDTO, HttpStatus.OK);
    }
}
```

---

## 1. Anotaciones de clase

- **`@RestController`**  
  Marca la clase como un controlador REST, que maneja solicitudes HTTP y devuelve respuestas (normalmente JSON).

- **`@RequestMapping("/usuarios")`**  
  Define que los endpoints de esta clase estarán bajo la ruta base `/usuarios`.

---

## 2. Métodos principales

### **2.1. Método `login`**

**Endpoint:** `/usuarios/login` (POST)  
- **Función:**  
  Autentica al usuario y devuelve un token JWT.

- **Parámetros:**  
  - `@RequestBody UsuarioLoginDTO`: Recibe los datos del usuario (nombre de usuario y contraseña).

- **Lógica:**
  1. **Autenticación:**  
     - Usa `authenticationManager` para validar las credenciales.
     - Si son correctas, genera un objeto `Authentication` con los datos del usuario autenticado.
  2. **Generación del token:**  
     - Utiliza `tokenService` para crear un token JWT firmado con las claves RSA.
  3. **Respuesta:**  
     - Devuelve el token JWT como un `String`.

---

### **2.2. Método `register`**

**Endpoint:** `/usuarios/register` (POST)  
- **Función:**  
  Registra a un nuevo usuario en el sistema.

- **Parámetros:**  
  - `@RequestBody UsuarioRegisterDTO`: Recibe los datos del usuario para el registro.

- **Lógica:**
  1. **Registro:**  
     - Llama a `customUserDetailsService.registerUser` para registrar al usuario en la base de datos.
     - Codifica la contraseña y asigna roles, si es necesario.
  2. **Respuesta:**  
     - Devuelve un objeto `UsuarioRegisterDTO` con los datos del usuario registrado.
     - Utiliza el código de estado HTTP 200 (`HttpStatus.OK`).

---

## 3. Integración con Spring Security

- **`AuthenticationManager`:**  
  Maneja el proceso de autenticación.
  - Valida las credenciales.
  - Devuelve un objeto `Authentication` con la información del usuario autenticado.

- **`CustomUserDetailsService`:**  
  Gestiona la lógica de registro de usuarios.

- **`TokenService`:**  
  Genera y valida tokens JWT.

---

## Resumen de la funcionalidad

1. **Autenticación (`/login`):**
   - Valida las credenciales con `AuthenticationManager`.
   - Genera un token JWT con `TokenService`.

2. **Registro (`/register`):**
   - Registra un nuevo usuario con `CustomUserDetailsService`.
   - Devuelve los datos del usuario registrado.

3. **Seguridad:**  
   - Integra con Spring Security para autenticar y registrar usuarios de forma segura.

4. **Respuestas:**  
   - Devuelve un token JWT en el login.
   - Retorna los datos del usuario registrado en el registro.

