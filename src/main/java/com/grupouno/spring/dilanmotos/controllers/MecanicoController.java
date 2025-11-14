package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Mecanico;
import com.grupouno.spring.dilanmotos.repositories.MecanicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
public class MecanicoController {

    @Autowired
    private MecanicoRepository mecanicoRepository; 

    @GetMapping("/mecanico")
    public String mostrarMecanicos(@RequestParam(value = "search", required = false) String search, Model model) {
    List<Mecanico> mecanico = (search != null && !search.isEmpty())
            
            ? mecanicoRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(search, search)
            : mecanicoRepository.findAll();

    model.addAttribute("mecanicos", mecanico);
    model.addAttribute("nuevoMecanico", new Mecanico());
    return "mecanico";
    }

    @PostMapping("/mecanico")
    public String guardarMecanico(@Valid @ModelAttribute("nuevoMecanico") Mecanico mecanico, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("mecanico", mecanicoRepository.findAll());
            return "mecanico";
        }
        mecanicoRepository.save(mecanico);
        return "redirect:/mecanico";
    }

    @GetMapping("/mecanico/editar/{id}")
    public String editarMecanico(@PathVariable("id") int id, Model model) {
        Mecanico mecanico = mecanicoRepository.findById(id).orElse(null);
        model.addAttribute("mecanicoEditado", mecanico);
        return "editar_mecanico";
    }

    @PostMapping("/mecanico/actualizar")
    public String actualizarMecanico(@Valid @ModelAttribute("mecanicoEditado") Mecanico mecanico, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_mecanico";
        }
        mecanicoRepository.save(mecanico);
        return "redirect:/mecanico";
    }

    @GetMapping("/mecanico/eliminar/{id}")
    public String eliminarMecanico(@PathVariable("id") int id) {
        mecanicoRepository.deleteById(id);
        return "redirect:/mecanico";
    }

}
