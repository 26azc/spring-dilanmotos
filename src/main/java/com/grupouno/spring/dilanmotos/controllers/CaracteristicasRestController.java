package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Caracteristicas;
import com.grupouno.spring.dilanmotos.repositories.CaracteristicasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CaracteristicasRestController {

    @Autowired
    private CaracteristicasRepository caracteristicasRepository;

    @GetMapping("/caracteristicas")
    public List<Caracteristicas> listarCaracteristicas() {
        return caracteristicasRepository.findAll();
    }
}
