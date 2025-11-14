package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Caracteristicas;
import com.grupouno.spring.dilanmotos.repositories.CaracteristicasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CaracteristicasController {

    @Autowired
    private CaracteristicasRepository caracteristicasRepository;

    @GetMapping("/caracteristicas")
    public String mostrarCaracteristicas(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Caracteristicas> resultados = (search != null && !search.isEmpty())
            ? caracteristicasRepository.findByDescripcionContainingIgnoreCase(search)
            : caracteristicasRepository.findAll();

        model.addAttribute("caracteristicas", resultados);
        model.addAttribute("nuevaCaracteristica", new Caracteristicas());
        return "caracteristicas";
    }

    @PostMapping("/caracteristicas")
    public String guardarCaracteristica(@Valid @ModelAttribute("nuevaCaracteristica") Caracteristicas caracteristica, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("caracteristicas", caracteristicasRepository.findAll());
            return "caracteristicas";
        }
        caracteristicasRepository.save(caracteristica);
        return "redirect:/caracteristicas";
    }

    @GetMapping("/caracteristicas/editar/{id}")
    public String editarCaracteristica(@PathVariable("id") int id, Model model) {
        Caracteristicas caracteristica = caracteristicasRepository.findById(id).orElse(null);
        model.addAttribute("caracteristicaEditada", caracteristica);
        return "editar_caracteristicas";
    }

    @PostMapping("/caracteristicas/actualizar")
    public String actualizarCaracteristica(@Valid @ModelAttribute("caracteristicaEditada") Caracteristicas caracteristica, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_caracteristicas";
        }
        caracteristicasRepository.save(caracteristica);
        return "redirect:/caracteristicas";
    }

    @GetMapping("/caracteristicas/eliminar/{id}")
    public String eliminarCaracteristica(@PathVariable("id") int id) {
        caracteristicasRepository.deleteById(id);
        return "redirect:/caracteristicas";
    }
}