package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "http://localhost:5173") // Permite la conexión con tu Vite/React
public class MarcaRestController {

    @Autowired
    private MarcaRepository marcaRepository;

    // 1. LISTAR Y BUSCAR (Por nombre)
    @GetMapping
    public ResponseEntity<List<Marca>> listarMarcas(@RequestParam(value = "search", required = false) String search) {
        List<Marca> marcas = (search != null && !search.isEmpty())
            ? marcaRepository.findByNombreContainingIgnoreCase(search)
            : marcaRepository.findAll();
        return ResponseEntity.ok(marcas);
    }

    // 2. BUSCAR POR ID (Para el buscador de ID exacto)
    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarcaPorId(@PathVariable int id) {
        return marcaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. GUARDAR (POST)
    @PostMapping
    public ResponseEntity<?> guardarMarca(@Valid @RequestBody Marca marca) {
        // Opcional: Validar si el nombre ya existe para evitar duplicados
        Marca nuevaMarca = marcaRepository.save(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    // 4. ACTUALIZAR (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMarca(@PathVariable int id, @Valid @RequestBody Marca marcaDetalles) {
        return marcaRepository.findById(id).map(marcaActualizar -> {
            marcaActualizar.setNombre(marcaDetalles.getNombre());
            // Agrega aquí otros campos si tu modelo 'Marca' tiene más (ej. descripción)
            
            marcaRepository.save(marcaActualizar);
            return ResponseEntity.ok(marcaActualizar);
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. ELIMINAR (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMarca(@PathVariable int id) {
        if (!marcaRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            marcaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Esto previene errores si la marca está siendo usada por un Producto/Moto (Integridad referencial)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar: La marca tiene registros asociados.");
        }
    }
}