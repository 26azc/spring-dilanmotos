package com.grupouno.spring.dilanmotos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;


@RestController
@RequestMapping("/api")
public class MotoRestController {
   @Autowired 
    private MotoRepository motoRepository;

    @GetMapping("/moto")
    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }   
}
