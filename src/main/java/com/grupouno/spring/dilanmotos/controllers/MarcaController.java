package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/marca")
   public String mostrarMarcas(@RequestParam(value = "search", required = false) String search, Model model) {
    List<Marca> marca = (search != null && !search.isEmpty())
            
            ? marcaRepository.findByNombreContainingIgnoreCase(search)
            : marcaRepository.findAll();

    model.addAttribute("marcas", marca);
    model.addAttribute("nuevaMarca", new Marca());
    return "marca";
}

    @PostMapping("/marca")
    public String guardarMarca(@Valid @ModelAttribute("nuevaMarca") Marca marca, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("marca", marcaRepository.findAll());
            return "marca";
        }
        marcaRepository.save(marca);
        return "redirect:/marca";
    }

    @GetMapping("/marca/editar/{id}")
    public String editarMarca(@PathVariable("id") int id, Model model) {
        Marca marca = marcaRepository.findById(id).orElse(null);
        model.addAttribute("marcaEditado", marca);
        return "editar_marca";
    }

    @PostMapping("/marca/actualizar")
    public String actualizarMarca(@Valid @ModelAttribute("marcaEditada") Marca marca, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_marca";
        }
        marcaRepository.save(marca);
        return "redirect:/marca";
    }

    @GetMapping("/marca/eliminar/{id}")
    public String eliminarMarca(@PathVariable("id") int id) {
        marcaRepository.deleteById(id);
        return "redirect:/marca";
    }
} 