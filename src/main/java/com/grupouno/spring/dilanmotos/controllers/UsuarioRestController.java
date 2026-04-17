package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.models.Referencia;
import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import com.grupouno.spring.dilanmotos.repositories.ReferenciaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Usuarios", description = "Controlador para la gestión completa de usuarios y autenticación")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private ReferenciaRepository referenciaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- 💡 NUEVO MÉTODO: LOGIN ---
    @Operation(summary = "Autenticar usuario", description = "Verifica credenciales y retorna los datos del usuario incluyendo el ID")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String contrasena = credenciales.get("contrasena");

        // Buscamos al usuario por correo usando el método que ya tienes en tu repo
        return usuarioRepository.findByCorreoConMotos(correo)
                .map(usuario -> {
                    // Comparamos la contraseña plana con la encriptada
                    if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
                        return ResponseEntity.ok(usuario); // Retorna el usuario completo (con su ID)
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"));
    }

    @Operation(summary = "Obtener lista de usuarios", description = "Retorna todos los usuarios o filtra por nombre/correo si se envía el parámetro search")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(
            @RequestParam(value = "search", required = false) String search) {
        List<Usuarios> usuarios = (search != null && !search.isEmpty())
                ? usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(search, search)
                : usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario con contraseña encriptada")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    // Se quita "/registrar" para que el POST a /api/usuarios funcione desde el CRUD
    @PostMapping
    @Transactional
    public ResponseEntity<?> guardarUsuario(@RequestBody Map<String, Object> datos) {
        try {
            // 1. Guardar Usuario
            Usuarios usuario = new Usuarios();
            usuario.setNombre((String) datos.get("nombre"));
            usuario.setCorreo((String) datos.get("correo"));
            usuario.setContrasena(passwordEncoder.encode((String) datos.get("contrasena")));
            Usuarios usuarioGuardado = usuarioRepository.save(usuario);

            // 2. Obtener la referencia del catálogo
            Integer idRef = Integer.parseInt(datos.get("idReferencia").toString());
            Referencia ref = referenciaRepository.findById(idRef)
                    .orElseThrow(() -> new RuntimeException("Modelo no encontrado en el catálogo"));

            // 3. Clonar los datos a la tabla 'moto' del usuario
            Moto motoUsuario = new Moto();
            motoUsuario.setModelo(ref.getNombre());
            motoUsuario.setCilindraje(ref.getCilindraje());
            motoUsuario.setMarca(ref.getMarca());
            motoUsuario.setUsuario(usuarioGuardado);

            motoRepository.save(motoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar usuario por ID", description = "Modifica los datos de un usuario existente.")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable int id, @Valid @RequestBody Usuarios usuarioDetalles) {
        return usuarioRepository.findById(id).map(usuarioActualizar -> {
            usuarioActualizar.setNombre(usuarioDetalles.getNombre());
            usuarioActualizar.setCorreo(usuarioDetalles.getCorreo());

            if (usuarioDetalles.getContrasena() != null && !usuarioDetalles.getContrasena().isEmpty()) {
                usuarioActualizar.setContrasena(passwordEncoder.encode(usuarioDetalles.getContrasena()));
            }

            usuarioRepository.save(usuarioActualizar);
            return ResponseEntity.ok(usuarioActualizar);
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Borra permanentemente un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener perfil de usuario", description = "Retorna los datos del usuario y sus motos asociadas")
    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> obtenerPerfil(@PathVariable int id) {
        // Usamos findByCorreoConMotos o findById para traer al usuario
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElse(ResponseEntity.notFound().build());
    }
}