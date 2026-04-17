package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.TipoServicio;
import com.grupouno.spring.dilanmotos.repositories.TipoServicioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/tipo-servicio")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Tipo de Servicio", description = "Gestión de categorías de servicios de taller")
public class TipoServicioRestController {

    // 1. Declaramos el atributo como final
    private final TipoServicioRepository repository;

    // Spring inyecta automáticamente la dependencia aquí
    public TipoServicioRestController(TipoServicioRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Listar tipos de servicio", description = "Obtiene todos los servicios. Permite búsqueda por nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida con éxito"),
            @ApiResponse(responseCode = "400", description = "No existe el servicio solicitado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<TipoServicio> listar(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return repository.findByNombreContainingIgnoreCase(search);
        }

        return repository.findAll();
    }

    @Operation(summary = "Crear o actualizar tipo de servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de servicio creado o actualizado con exito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para la creación o actualización")
    })
    @PostMapping
    public TipoServicio guardar(@RequestBody TipoServicio tipoServicio) {
        return repository.save(tipoServicio);
    }

    @Operation(summary = "Eliminar tipo de servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servicio eliminado con exito"),
            @ApiResponse(responseCode = "404", description = "El ID del servicio no existe"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar tipo de servicio")
    @PutMapping("/{id}")
    public ResponseEntity<TipoServicio> actualizar(@PathVariable int id, @RequestBody TipoServicio detalle) {
        return repository.findById(id)
                .map(tipo -> {
                    // Sincronizado con los setters en minúscula corregidos en el modelo
                    tipo.setNombre(detalle.getNombre());
                    tipo.setDescripcion(detalle.getDescripcion());
                    return ResponseEntity.ok(repository.save(tipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}