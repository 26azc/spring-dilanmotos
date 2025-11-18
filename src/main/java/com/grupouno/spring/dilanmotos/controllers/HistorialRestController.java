package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Historial;
import com.grupouno.spring.dilanmotos.repositories.HistorialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HistorialRestController {

    @Autowired
    private HistorialRepository historialRepository;

    @GetMapping("/historiales")
    public List<Historial> listarHistoriales() {
        return historialRepository.findAll();
    }
}

