package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarcaRestController {

    @Autowired
    private MarcaRepository MarcaRepository;

    @GetMapping("/marca")
    public List<Marca> listarMarcas() {
        return MarcaRepository.findAll();
    }
}
