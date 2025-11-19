package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.PQRS;
import com.grupouno.spring.dilanmotos.repositories.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api")
public class PqrsRestController {

    @Autowired
    private PqrsRepository PqrsRepository;

    @GetMapping("/PQRS")
    public List<PQRS> listarPqrs() {
        return PqrsRepository.findAll();
    }
}