package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// IMPORTACIONES DE SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
// TAG: Agrupa los endpoints en la interfaz de Swagger
@Tag(name = "Usuarios", description = "Controlador para la gestión completa de usuarios (CRUD)")
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Obtener lista de usuarios", description = "Retorna todos los usuarios o filtra por nombre/correo si se envía el parámetro search")
    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(
            @RequestParam(value = "search", required = false) String search) {
        List<Usuarios> usuarios = (search != null && !search.isEmpty())
                ? usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(search, search)
                : usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario en la base de datos con la contraseña encriptada")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "409", description = "El correo ya existe")
    @PostMapping
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuarios usuario) {
        if (usuarioRepository.findByCorreoConMotos(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya está registrado");
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuarios nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    @Operation(summary = "Actualizar usuario por ID", description = "Modifica los datos de un usuario existente. Si se envía contraseña, se re-encripta.")
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

    @Operation(summary = "Eliminar usuario", description = "Borra permanentemente un usuario de la base de datos mediante su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}