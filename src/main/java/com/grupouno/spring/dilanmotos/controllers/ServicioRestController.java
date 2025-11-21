package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Servicio;
import com.grupouno.spring.dilanmotos.repositories.ServicioRepository;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.repositories.MecanicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioRestController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    // 📋 Listar todos los servicios
    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    // 🔍 Buscar por estado o comentario
    @GetMapping("/buscar")
    public List<Servicio> buscarServicios(@RequestParam("q") String query) {
        return servicioRepository.findByComentarioContainingIgnoreCaseOrEstadoServicioContainingIgnoreCase(query, query);
    }

    // 📄 Obtener un servicio por ID
    @GetMapping("/{id}")
    public Optional<Servicio> obtenerServicio(@PathVariable Integer id) {
        return servicioRepository.findById(id);
    }

    // ➕ Crear nuevo servicio
    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio servicio) {
        // Validar relaciones: usuario y mecánico deben existir
        if (servicio.getUsuario() != null) {
            usuarioRepository.findById(servicio.getUsuario().getIdUsuario())
                    .ifPresent(servicio::setUsuario);
        }
        if (servicio.getMecanico() != null) {
            mecanicoRepository.findById(servicio.getMecanico().getIdMecanico())
                    .ifPresent(servicio::setMecanico);
        }
        return servicioRepository.save(servicio);
    }

    // 🔄 Actualizar servicio existente
    @PutMapping("/{id}")
    public Servicio actualizarServicio(@PathVariable Integer id, @RequestBody Servicio servicio) {
        servicio.setIdServicio(id);

        if (servicio.getUsuario() != null) {
            usuarioRepository.findById(servicio.getUsuario().getIdUsuario())
                    .ifPresent(servicio::setUsuario);
        }
        if (servicio.getMecanico() != null) {
            mecanicoRepository.findById(servicio.getMecanico().getIdMecanico())
                    .ifPresent(servicio::setMecanico);
        }

        return servicioRepository.save(servicio);
    }

    // 🗑 Eliminar servicio
    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable Integer id) {
        servicioRepository.deleteById(id);
    }
}
