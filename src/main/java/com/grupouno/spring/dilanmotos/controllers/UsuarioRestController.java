package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

// 1. Cambiamos @Controller por @RestController
@RestController 
// 2. Definimos una ruta base clara para la API
@RequestMapping("/api/usuarios") 
public class UsuarioRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarUsuarios(@RequestParam(value = "search", required = false) String search) {
        List<Usuarios> usuarios = (search != null && !search.isEmpty())
            ? usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(search, search)
            : usuarioRepository.findAll();

        return ResponseEntity.ok(usuarios); // Devuelve HTTP 200 con la lista en JSON
    }

    // Petición POST para crear
    @PostMapping
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuarios usuario) {
        // Verificamos si el correo ya existe (buena práctica en APIs)
        if(usuarioRepository.findByCorreoConMotos(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya está registrado");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuarios nuevoUsuario = usuarioRepository.save(usuario);
        
        // Devuelve HTTP 201 (Creado) y el usuario guardado
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario); 
    }

    // Petición PUT para actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") int id, @Valid @RequestBody Usuarios usuarioDetalles) {
        Optional<Usuarios> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuarios usuarioActualizar = usuarioExistente.get();
        usuarioActualizar.setNombre(usuarioDetalles.getNombre());
        usuarioActualizar.setCorreo(usuarioDetalles.getCorreo());
        
        // Solo actualizamos la contraseña si enviaron una nueva
        if (usuarioDetalles.getContrasena() != null && !usuarioDetalles.getContrasena().isEmpty()) {
            usuarioActualizar.setContrasena(passwordEncoder.encode(usuarioDetalles.getContrasena()));
        }

        usuarioRepository.save(usuarioActualizar);
        return ResponseEntity.ok(usuarioActualizar);
    }

    // Petición DELETE para borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") int id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // HTTP 204 (Sin contenido, operación exitosa)
    }
}