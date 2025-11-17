package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    // Mostrar listado y formulario
    @GetMapping("/motos")
    public String mostrarMotos(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Moto> motos = (search != null && !search.isEmpty())
            ? motoRepository.findByModeloContainingIgnoreCase(search)
            : motoRepository.findAll();

        model.addAttribute("motos", motos);
        model.addAttribute("nuevoMoto", new Moto());
        return "motos";
    }

    // Guardar nueva moto
    @PostMapping("/motos")
    public String guardarMoto(
        @Valid @NonNull @ModelAttribute("nuevoMoto") Moto moto,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("motos", motoRepository.findAll());
            return "motos";
        }

        motoRepository.save(moto);
        return "redirect:/motos?creado";
    }
}