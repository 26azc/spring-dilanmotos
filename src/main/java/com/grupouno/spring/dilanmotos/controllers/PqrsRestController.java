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
@CrossOrigin(origins = "http://localhost:5173")
public class PqrsRestController {

    @Autowired
    private PqrsRepository repository;

    @GetMapping
    public List<PQRS> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PQRS crear(@RequestBody PQRS pqrs) {
        if (pqrs.getIdUsuario() == null) pqrs.setIdUsuario(1); // Temporal para pruebas
        return repository.save(pqrs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PQRS> actualizar(@PathVariable Integer id, @RequestBody PQRS datos) {
        return repository.findById(id).map(p -> {
            p.setEstado(datos.getEstado());
            p.setRespuesta_admin(datos.getRespuesta_admin());
            
            // Si el admin escribe una respuesta, se pone fecha de respuesta
            if (datos.getRespuesta_admin() != null && !datos.getRespuesta_admin().isEmpty()) {
                p.setFecha_respuesta(LocalDateTime.now());
            }
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}