package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
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
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    // --- LISTAR TODAS LAS MOTOS ---
    @GetMapping
    public List<Moto> listarMotos() {
        return motoRepository.findAll();
    }

    // --- BUSCAR POR MODELO ---
    @GetMapping("/buscar")
    public List<Moto> buscarPorModelo(@RequestParam("modelo") String modelo) {
        return motoRepository.findByModeloContainingIgnoreCase(modelo);
    }

    // --- REGISTRAR MOTO ---
    @PostMapping
    public ResponseEntity<?> registrarMoto(@RequestBody Moto nuevaMoto) {
        // Nota: Como en React no tenemos el "Authentication auth" de sesión clásica
        // fácilmente,
        // por ahora asignamos el usuario que venga en el JSON o el usuario ID 1.
        if (nuevaMoto.getUsuario() == null) {
            Usuarios porDefecto = usuarioRepository.findById(1).orElse(null);
            nuevaMoto.setUsuario(porDefecto);
        }

        Moto guardada = motoRepository.save(nuevaMoto);
        return ResponseEntity.ok(guardada);
    }

    // --- ACTUALIZAR MOTO ---
    @PutMapping("/{id}")
    public ResponseEntity<Moto> actualizarMoto(@PathVariable Integer id, @RequestBody Moto motoEditada) {
        return motoRepository.findById(id).map(m -> {
            m.setModelo(motoEditada.getModelo());
            m.setCilindraje(motoEditada.getCilindraje());
            m.setTipoReparacion(motoEditada.getTipoReparacion());
            m.setMarca(motoEditada.getMarca());
            return ResponseEntity.ok(motoRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    // --- ELIMINAR MOTO ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMoto(@PathVariable Integer id) {
        motoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}