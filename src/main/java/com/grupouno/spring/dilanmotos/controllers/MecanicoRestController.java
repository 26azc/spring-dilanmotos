package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Mecanico;
import com.grupouno.spring.dilanmotos.repositories.MecanicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MecanicoRestController {

    @Autowired
    private MecanicoRepository MecanicoRepository;

    @GetMapping("/mecanico")
    public List<Mecanico> listarMecanicos() {
        return MecanicoRepository.findAll();
    }    

}
