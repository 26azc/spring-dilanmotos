package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.PQRS;
import com.grupouno.spring.dilanmotos.repositories.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pqrs")
@CrossOrigin(origins = "http://localhost:5173") // Asegúrate de que este sea el puerto de tu React
public class PqrsRestController {

    @Autowired
    private PqrsRepository repository;

    // 1. Obtener todas las PQRS (Para el Administrador)
    @GetMapping
    public List<PQRS> listar() {
        return repository.findAll();
    }

    // 2. Obtener PQRS de un usuario específico (Para el Historial del Socio)
    // 💡 Este es el método que React llama como: /api/pqrs/usuario/ID
    @GetMapping("/usuario/{id}")
    public List<PQRS> listarPorUsuario(@PathVariable Integer id) {
        return repository.findByIdUsuario(id);
    }

    // 3. Crear una nueva PQRS (Desde el formulario del Socio o Admin)
    @PostMapping
    public PQRS crear(@RequestBody PQRS pqrs) {
        // Validamos que el ID de usuario no llegue nulo antes de guardar
        if (pqrs.getIdUsuario() == null) {
            // Si llega nulo por algún error en el front, podrías manejar un error 
            // o asignar uno por defecto, pero el Front ya debería enviarlo.
            pqrs.setIdUsuario(1); 
        }
        
        // La fecha de envío se asigna automáticamente por el @PrePersist en tu modelo PQRS.java
        return repository.save(pqrs);
    }

    // 4. Actualizar PQRS (Para que el Admin responda y cambie el estado)
    @PutMapping("/{id}")
    public ResponseEntity<PQRS> actualizar(@PathVariable Integer id, @RequestBody PQRS datos) {
        return repository.findById(id).map(p -> {
            // Actualizamos los campos de gestión
            p.setEstado(datos.getEstado());
            p.setRespuesta_admin(datos.getRespuesta_admin());
            
            // Si el administrador está enviando una respuesta, actualizamos la fecha de respuesta
            if (datos.getRespuesta_admin() != null && !datos.getRespuesta_admin().trim().isEmpty()) {
                p.setFecha_respuesta(LocalDateTime.now());
            }
            
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar una PQRS
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}