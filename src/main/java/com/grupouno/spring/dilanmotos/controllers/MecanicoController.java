package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Mecanico;
import com.grupouno.spring.dilanmotos.repositories.MecanicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MecanicoController {

    @Autowired
    private MecanicoRepository mecanicoRepository;

    // Mostrar listado y formulario
    @GetMapping("/mecanico")
    public String mostrarMecanicos(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Mecanico> mecanicos = (search != null && !search.isEmpty())
            ? mecanicoRepository.findByNombreContainingIgnoreCase(search)
            : mecanicoRepository.findAll();

        model.addAttribute("mecanicos", mecanicos);
        model.addAttribute("nuevoMecanico", new Mecanico());
        return "mecanico";
    }

    // Guardar nuevo mecánico
    @PostMapping("/mecanico")
    public String guardarMecanico(
        @Valid @NonNull @ModelAttribute("nuevoMecanico") Mecanico mecanico,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("mecanicos", mecanicoRepository.findAll());
            return "mecanico";
        }

        mecanicoRepository.save(mecanico);
        return "redirect:/mecanico?creado";
    }

    // Mostrar formulario de edición
    @GetMapping("/mecanico/editar/{id}")
    public String editarMecanico(@PathVariable("id") int id, Model model) {
        return mecanicoRepository.findById(id)
            .map(mecanico -> {
                model.addAttribute("mecanicoEditado", mecanico);
                return "editar_mecanico";
            })
            .orElse("redirect:/mecanico?error=not_found");
    }

    // Actualizar mecánico
    @PostMapping("/mecanico/actualizar")
    public String actualizarMecanico(
        @Valid @NonNull @ModelAttribute("mecanicoEditado") Mecanico mecanico,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "editar_mecanico";
        }

        mecanicoRepository.save(mecanico);
        return "redirect:/mecanico?actualizado";
    }

    // Eliminar mecánico
    @GetMapping("/mecanico/eliminar/{id}")
    public String eliminarMecanico(@PathVariable("id") int id) {
        if (mecanicoRepository.existsById(id)) {
            mecanicoRepository.deleteById(id);
            return "redirect:/mecanico?eliminado";
        }
        return "redirect:/mecanico?error=not_found";
    }
}