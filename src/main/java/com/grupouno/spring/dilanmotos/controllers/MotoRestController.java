package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/motos")
@CrossOrigin(origins = "http://localhost:5173")
public class MotoRestController {

    @Autowired
    private MotoRepository motoRepository;

    // --- ESTE ES EL QUE TE FALTABA PARA EL CHAT ---
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerMotoPorUsuario(@PathVariable Integer idUsuario) {
        return motoRepository.findMotoCompletaPorUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/marca/{idMarca}")
    public List<Moto> obtenerPorMarca(@PathVariable Integer idMarca) {
        return motoRepository.findByMarca_IdMarca(idMarca);
    }
}