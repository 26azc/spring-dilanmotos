package com.grupouno.spring.dilanmotos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupouno.spring.dilanmotos.models.Productos;
import com.grupouno.spring.dilanmotos.repositories.ProductosRepository;

@RestController
@RequestMapping("/api")
public class ProductosRestController {
    
    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/productos")
    public List<Productos> listarProductos(){
        return productosRepository.findAll();
    }
}
