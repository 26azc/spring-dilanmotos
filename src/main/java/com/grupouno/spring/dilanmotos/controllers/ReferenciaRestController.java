package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Referencia;
import com.grupouno.spring.dilanmotos.repositories.ReferenciaRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/referencias")
@CrossOrigin(origins = "http://localhost:5173")
public class ReferenciaRestController {

    @Autowired
    private ReferenciaRepository referenciaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    // Listar todas las referencias o filtrar por marca
    @GetMapping
    public List<Referencia> listarReferencias(@RequestParam(value = "marcaId", required = false) Integer marcaId,
                                              @RequestParam(value = "search", required = false) String search) {
        if (marcaId != null) {
            return referenciaRepository.findByMarca_IdMarca(marcaId);
        } else if (search != null && !search.isEmpty()) {
            return referenciaRepository.findByNombreContainingIgnoreCase(search);
        } else {
            return referenciaRepository.findAll();
        }
    }
}
