package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.PQRS;
import com.grupouno.spring.dilanmotos.repositories.PqrsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pqrs")
public class PqrsRestController {

    @Autowired
    private PqrsRepository pqrsRepository;

    // Listar y Buscar
    @GetMapping
    public ResponseEntity<List<PQRS>> listarPqrs(@RequestParam(value = "search", required = false) String search) {
        List<PQRS> pqrs = (search != null && !search.isEmpty())
                ? pqrsRepository.findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase(search, search)
                : pqrsRepository.findAll();
        return ResponseEntity.ok(pqrs);
    }

    // Crear
    @PostMapping
    public ResponseEntity<PQRS> guardarPqrs(@Valid @RequestBody PQRS pqrs) {
        // Lógica de valores iniciales (Igual que en tu Controller original)
        pqrs.setIdUsuario(1); // TODO: Reemplazar con ID real tras implementar JWT
        pqrs.setFecha(LocalDateTime.now());
        pqrs.setEstado("PENDIENTE");
        pqrs.setRespuesta_admin("Sin respuesta.");
        pqrs.setCalificacion_servicio("-");
        pqrs.setComentario_servicio("-");
        pqrs.setFecha_respuesta(null);

        PQRS nuevoPqrs = pqrsRepository.save(pqrs);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPqrs);
    }

    // Obtener uno solo (Para cargar en el formulario de edición en React)
    @GetMapping("/{id}")
    public ResponseEntity<PQRS> obtenerPqrs(@PathVariable("id") int id) {
        return pqrsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<PQRS> actualizarPqrs(@PathVariable("id") int id, @Valid @RequestBody PQRS pqrsDetalles) {
        return pqrsRepository.findById(id)
                .map(pqrs -> {
                    pqrs.setAsunto(pqrsDetalles.getAsunto());
                    pqrs.setTipo(pqrsDetalles.getTipo());
                    pqrs.setDescripcion(pqrsDetalles.getDescripcion());
                    // Mantener o actualizar otros campos según necesites
                    return ResponseEntity.ok(pqrsRepository.save(pqrs));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPqrs(@PathVariable("id") int id) {
        if (pqrsRepository.existsById(id)) {
            pqrsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}