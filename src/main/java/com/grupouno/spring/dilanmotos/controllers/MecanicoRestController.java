package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Mecanico;
import com.grupouno.spring.dilanmotos.repositories.MecanicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Mecanico", description = "Gestion del personal del taller")
public class MecanicoRestController {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoRestController(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    @GetMapping
    public List<Mecanico> listar(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return mecanicoRepository.findByNombreContainingIgnoreCase(search);
        }
        return mecanicoRepository.findAll();
    }

    @PostMapping
    public Mecanico guardar(@RequestBody Mecanico mecanico) {
        return mecanicoRepository.save(mecanico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> actualizar(@PathVariable int id, @RequestBody Mecanico mecanico) {
        return mecanicoRepository.findById(id)
                .map(mecanicoExistente -> {
                    mecanicoExistente.setNombre(mecanico.getNombre());
                    mecanicoExistente.setEspecialidad(mecanico.getEspecialidad());
                    mecanicoExistente.setTelefono(mecanico.getTelefono());
                    // Guardamos los cambios antes de retornar
                    Mecanico actualizado = mecanicoRepository.save(mecanicoExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!mecanicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mecanicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}