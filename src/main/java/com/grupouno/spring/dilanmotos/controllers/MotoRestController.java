package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // 👈 CRUCIAL: Si dice @Controller dará 404
@RequestMapping("/api/motos")
@CrossOrigin(origins = "http://localhost:5173")
public class MotoRestController {

    @Autowired
    private MotoRepository motoRepository;

    @GetMapping("/marca/{idMarca}")
    public List<Moto> obtenerPorMarca(@PathVariable Integer idMarca) {
        System.out.println(">>> Entrando al endpoint con ID: " + idMarca);
        return motoRepository.findByMarca_IdMarca(idMarca);
    }
}