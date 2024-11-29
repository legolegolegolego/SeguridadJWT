
# Análisis en detalle: CustomUserDetailsService.java

## Código:
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));

        UserDetails userDetails = User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().split(","))
                .build();

        return userDetails;
    }

    public UsuarioRegisterDTO registerUser(UsuarioRegisterDTO usuarioRegisterDTO) {
        if (usuarioRepository.findByUsername(usuarioRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setPassword(passwordEncoder.encode(usuarioRegisterDTO.getPassword()));
        newUsuario.setUsername(usuarioRegisterDTO.getUsername());
        newUsuario.setRoles(usuarioRegisterDTO.getRoles());

        usuarioRepository.save(newUsuario);

        return usuarioRegisterDTO;
    }
}
```

---

## 1. Anotaciones de clase

- **`@Service`**  
  Marca esta clase como un servicio gestionado por Spring. Permite que sea inyectada en otros componentes del sistema.

- **`implements UserDetailsService`**  
  Indica que esta clase implementa la interfaz `UserDetailsService`, requerida por Spring Security para cargar detalles de usuarios durante la autenticación.

---

## 2. Atributos de la clase

### **`UsuarioRepository`**
- **Propósito:**  
  Es un repositorio que extiende `JpaRepository` y permite realizar operaciones CRUD en la entidad `Usuario`.
- **Inyección automática:**  
  Spring inyecta automáticamente este repositorio.

### **`PasswordEncoder`**
- **Propósito:**  
  Responsable de codificar contraseñas antes de almacenarlas en la base de datos.
- **Inyección automática:**  
  Configurado como un bean en la clase `SecurityConfig`.

---

## 3. Métodos principales

### **3.1. Método `loadUserByUsername`**

**Propósito:**  
Carga un usuario desde la base de datos y lo convierte en un objeto `UserDetails`.

**Flujo:**
1. Busca al usuario en la base de datos utilizando `usuarioRepository.findByUsername`.
2. Lanza una excepción `UsernameNotFoundException` si el usuario no existe.
3. Convierte la entidad `Usuario` en un objeto `UserDetails` utilizando el constructor de la clase `User`.
4. Retorna el objeto `UserDetails` a Spring Security.

---

### **3.2. Método `registerUser`**

**Propósito:**  
Registra un nuevo usuario en la base de datos.

**Flujo:**
1. Verifica si el usuario ya existe en la base de datos.
2. Crea una nueva instancia de `Usuario`.
3. Hashea la contraseña con `PasswordEncoder`.
4. Guarda el usuario en la base de datos utilizando `usuarioRepository.save`.
5. Devuelve un objeto `UsuarioRegisterDTO` como confirmación del registro.

---

## 4. Resumen de la funcionalidad

La clase **`CustomUserDetailsService`**:
1. **Carga detalles de usuarios:**  
   Implementa el método `loadUserByUsername` para integrar Spring Security con la base de datos.
2. **Registra nuevos usuarios:**  
   Gestiona la lógica de registro de usuarios, asegurando que las contraseñas se almacenen de forma segura.
3. **Seguridad:**  
   Utiliza `PasswordEncoder` para proteger contraseñas y se integra con Spring Security para la autenticación.

---

## 5. Flujo detallado

### **5.1. Método `loadUserByUsername`**
1. Busca al usuario en la base de datos por su nombre de usuario.
2. Lanza una excepción si no se encuentra.
3. Convierte el usuario en un objeto `UserDetails`.
4. Devuelve el objeto `UserDetails` para su uso en la autenticación.

### **5.2. Método `registerUser`**
1. Valida que el usuario no exista.
2. Crea un nuevo usuario y hashea su contraseña.
3. Guarda el usuario en la base de datos.
4. Devuelve los datos del usuario registrado.

---

## Conexión con Spring Security

- **Autenticación:**  
  `loadUserByUsername` proporciona la información del usuario durante el proceso de autenticación.
- **Registro:**  
  `registerUser` gestiona la lógica de creación de nuevos usuarios.
