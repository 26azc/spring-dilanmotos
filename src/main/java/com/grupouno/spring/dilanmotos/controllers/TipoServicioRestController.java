package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.TipoServicio;
import com.grupouno.spring.dilanmotos.repositories.TipoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TipoServicioRestController {

    @Autowired
    private TipoServicioRepository TipoServicioRepository ;

    @GetMapping("/tipoServicio")
    public List<TipoServicio> listarTipoServicios() {
        return TipoServicioRepository.findAll();
    }    

}
