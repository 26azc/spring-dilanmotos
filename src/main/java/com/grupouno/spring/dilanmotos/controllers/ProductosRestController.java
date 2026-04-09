package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Productos;
import com.grupouno.spring.dilanmotos.repositories.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173") // 💡 Importante para React
public class ProductosRestController {

    @Autowired
    private ProductosRepository productosRepository;

    // 💡 Habilita el método GET (Evita Error 405 al cargar)
    @GetMapping
    public List<Productos> listar() {
        return productosRepository.findAll();
    }

    // 💡 Habilita el método POST (Evita Error 405 al guardar)
    @PostMapping
    public Productos guardar(@RequestBody Productos producto) {
        return productosRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        productosRepository.deleteById(id);
    }
}