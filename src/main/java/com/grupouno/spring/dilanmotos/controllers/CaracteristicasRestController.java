package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Caracteristicas;
import com.grupouno.spring.dilanmotos.repositories.CaracteristicasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// IMPORTACIONES DE SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

/*
DIRECCION DE ACCESO A SWAGGER http://localhost:8080/swagger-ui/index.html */

@RestController
@RequestMapping("/api/caracteristicas")
@CrossOrigin(origins = "http://localhost:5173")
// TAG: Agrupa los endpoints en la interfaz de Swagger
@Tag(name = "Características", description = "Gestión de las especificaciones técnicas de los vehículos")
public class CaracteristicasRestController {

    @Autowired
    private CaracteristicasRepository repository;

    @Operation(summary = "Listar todas las características", description = "Obtiene la lista completa de características registradas en la base de datos")
    @GetMapping
    public List<Caracteristicas> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Crear/Actualizar característica", description = "Guarda una nueva característica o actualiza una existente si se envía el ID")
    @ApiResponse(responseCode = "200", description = "Característica procesada con éxito")
    @PostMapping
    public Caracteristicas guardar(@RequestBody Caracteristicas c) {
        return repository.save(c);
    }

    @Operation(summary = "Eliminar característica por ID", description = "Remueve permanentemente el registro de la base de datos")
    @ApiResponse(responseCode = "204", description = "Eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el ID especificado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}