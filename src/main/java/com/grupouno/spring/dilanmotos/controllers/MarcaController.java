package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    // Mostrar listado y formulario
    @GetMapping("/marca")
    public String mostrarMarcas(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Marca> marcas = (search != null && !search.isEmpty())
            ? marcaRepository.findByNombreContainingIgnoreCase(search)
            : marcaRepository.findAll();

        model.addAttribute("marcas", marcas);
        model.addAttribute("nuevaMarca", new Marca());
        return "marca";
    }

    // Guardar nueva marca
    @PostMapping("/marca")
    public String guardarMarca(
        @Valid @NonNull @ModelAttribute("nuevaMarca") Marca marca,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("marcas", marcaRepository.findAll());
            return "marca";
        }

        marcaRepository.save(marca);
        return "redirect:/marca?creado";
    }

    // Mostrar formulario de edición
    @GetMapping("/marca/editar/{id}")
    public String editarMarca(@PathVariable("id") int id, Model model) {
        return marcaRepository.findById(id)
            .map(marca -> {
                model.addAttribute("marcaEditada", marca);
                return "editar_marca";
            })
            .orElse("redirect:/marca?error=not_found");
    }

    // Actualizar marca
    @PostMapping("/marca/actualizar")
    public String actualizarMarca(
        @Valid @NonNull @ModelAttribute("marcaEditada") Marca marca,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "editar_marca";
        }

        marcaRepository.save(marca);
        return "redirect:/marca?actualizado";
    }

    // Eliminar marca
    @GetMapping("/marca/eliminar/{id}")
    public String eliminarMarca(@PathVariable("id") int id) {
        if (marcaRepository.existsById(id)) {
            marcaRepository.deleteById(id);
            return "redirect:/marca?eliminado";
        }
        return "redirect:/marca?error=not_found";
    }
}