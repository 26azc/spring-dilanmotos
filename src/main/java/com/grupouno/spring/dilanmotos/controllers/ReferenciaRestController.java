package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Referencia;
import com.grupouno.spring.dilanmotos.repositories.ReferenciaRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;



@RestController
@RequestMapping("/api/referencias")
@CrossOrigin(origins = "http://localhost:5173")
public class ReferenciaRestController {

    @Autowired
    private ReferenciaRepository referenciaRepository;

    // 1. Método para listar (GET)
    @GetMapping
    public List<Referencia> listar(@RequestParam(required = false) Integer marcaId) {
        if (marcaId != null) {
            return referenciaRepository.findByMarca_IdMarca(marcaId);
        }
        return referenciaRepository.findAll();
    }

    // 2. MÉTODO PARA GUARDAR (POST) - ¡Este es el que evita el error 405!
    @PostMapping
    public ResponseEntity<Referencia> crear(@RequestBody Referencia referencia) {
        Referencia nueva = referenciaRepository.save(referencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // 3. Método para actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Referencia> actualizar(@PathVariable Integer id, @RequestBody Referencia referenciaActualizada) {
        return referenciaRepository.findById(id)
                .map(referencia -> {
                    referencia.setNombre(referenciaActualizada.getNombre());
                    referencia.setMarca(referenciaActualizada.getMarca());
                    Referencia actualizada = referenciaRepository.save(referencia);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Método para eliminar (DELETE)
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    System.out.println("Intentando eliminar ID: " + id); // Esto saldrá en tu consola de STS/IntelliJ
    if (referenciaRepository.existsById(id)) {
        referenciaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
}

}
