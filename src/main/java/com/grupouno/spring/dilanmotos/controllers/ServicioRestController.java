package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Servicio;
import com.grupouno.spring.dilanmotos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "http://localhost:5173")
public class ServicioRestController {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @GetMapping
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @GetMapping("/usuario/{id}")
    public List<Servicio> listarPorUsuario(@PathVariable Integer id) {
        return servicioRepository.findByUsuario_IdUsuario(id);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Servicio servicio) {
        return guardarOActualizar(servicio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Servicio servicioDetalles) {
        if (!servicioRepository.existsById(id)) return ResponseEntity.notFound().build();
        servicioDetalles.setIdServicio(id); // Aseguramos que use el ID de la URL
        return guardarOActualizar(servicioDetalles);
    }

    // Método privado para evitar repetir la lógica de vinculación de IDs
    private ResponseEntity<?> guardarOActualizar(Servicio servicio) {
        try {
            if (servicio.getUsuario() != null) {
                usuarioRepository.findById(servicio.getUsuario().getIdUsuario()).ifPresent(servicio::setUsuario);
            }
            if (servicio.getMoto() != null) {
                motoRepository.findById(servicio.getMoto().getIdMoto()).ifPresent(servicio::setMoto);
            }
            if (servicio.getTipoServicio() != null) {
                tipoServicioRepository.findById(servicio.getTipoServicio().getId_tipo_servicio()).ifPresent(servicio::setTipoServicio);
            }
            return ResponseEntity.ok(servicioRepository.save(servicio));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error de persistencia: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            if (!servicioRepository.existsById(id)) return ResponseEntity.notFound().build();
            servicioRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"mensaje\": \"Eliminado correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar: " + e.getMessage());
        }
    }
}