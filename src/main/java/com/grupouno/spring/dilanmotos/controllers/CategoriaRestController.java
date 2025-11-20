package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Categoria;
import com.grupouno.spring.dilanmotos.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaRestController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias(@RequestParam(required = false) String nombre) {
        if (nombre != null) {
            return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
        }
        return categoriaRepository.findAll();
    }
}
