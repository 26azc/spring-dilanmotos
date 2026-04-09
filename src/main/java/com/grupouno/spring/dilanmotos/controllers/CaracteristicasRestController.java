package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Caracteristicas;
import com.grupouno.spring.dilanmotos.repositories.CaracteristicasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
@CrossOrigin(origins = "http://localhost:5173")
public class CaracteristicasRestController {

    @Autowired
    private CaracteristicasRepository repository;

    @GetMapping
    public List<Caracteristicas> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Caracteristicas guardar(@RequestBody Caracteristicas c) {
        return repository.save(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}