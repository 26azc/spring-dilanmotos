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

@RestController 
@RequestMapping("/api/usuarios")
// 1. AGREGAMOS CORS (Sin esto, el navegador bloquea PUT y DELETE)
@CrossOrigin(origins = "http://localhost:5173") 
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
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuarios usuario) {
        if(usuarioRepository.findByCorreoConMotos(usuario.getCorreo()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya está registrado");
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuarios nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario); 
    }

    // 2. CORRECCIÓN EN EL PUT: Aseguramos que el ID se capture bien
@PutMapping("/{id}") // El nombre aquí debe ser igual...
public ResponseEntity<?> actualizarUsuario(@PathVariable int id, @Valid @RequestBody Usuarios usuarioDetalles) { 
    // ...al nombre de esta variable ^
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

    // 3. CORRECCIÓN EN EL DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}