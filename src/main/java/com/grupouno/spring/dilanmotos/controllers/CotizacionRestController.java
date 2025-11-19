package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Cotizacion;
import com.grupouno.spring.dilanmotos.repositories.CotizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CotizacionRestController {

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @GetMapping("/cotizacion")
    public List<Cotizacion> listarCotizaciones() {
        return cotizacionRepository.findAll();
    }
}
