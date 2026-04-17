package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/motos")
@CrossOrigin(origins = "http://localhost:5173")
public class MotoRestController {

    @Autowired
    private MotoRepository motoRepository;

    // Listar todas las motos (Para que el .map() de React funcione al inicio)
    @GetMapping
    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    // Obtener moto por ID de usuario (Para el Mecánico Virtual)
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerMotoPorUsuario(@PathVariable Integer idUsuario) {
        return motoRepository.findMotoCompletaPorUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener por ID específico envuelto en Lista (Evita el error .map de React)
    @GetMapping("/{id}")
    public List<Moto> obtenerMotoPorId(@PathVariable Integer id) {
        return motoRepository.findById(id)
                .map(List::of) 
                .orElse(List.of());
    }

    @PostMapping
    public Moto crearMoto(@RequestBody Moto moto) {
        return motoRepository.save(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moto> actualizarMoto(@PathVariable Integer id, @RequestBody Moto motoActualizada) {
        return motoRepository.findById(id)
                .map(moto -> {
                    // Sincronizado con tus campos reales de la clase Moto
                    moto.setMarca(motoActualizada.getMarca());
                    moto.setModelo(motoActualizada.getModelo());
                    moto.setCilindraje(motoActualizada.getCilindraje());
                    moto.setTipoServicio(motoActualizada.getTipoServicio());
                    
                    Moto motoGuardada = motoRepository.save(moto);
                    return ResponseEntity.ok(motoGuardada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMoto(@PathVariable Integer id) {
        if (motoRepository.existsById(id)) {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}