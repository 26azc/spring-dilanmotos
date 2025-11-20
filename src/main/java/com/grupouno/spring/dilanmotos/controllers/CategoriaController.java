package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Categoria;
import com.grupouno.spring.dilanmotos.repositories.CategoriaRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Mostrar listado y formulario
    @GetMapping("/categoria")
    public String mostrarCategorias(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Categoria> categorias = (search != null && !search.isEmpty())
            ? categoriaRepository.findByNombreContainingIgnoreCase(search)
            : categoriaRepository.findAll();

        model.addAttribute("categorias", categorias);
        model.addAttribute("nuevaCategoria", new Categoria());
        return "categoria";
    }

    @PostMapping("/crear")
    public String crearCategoria(
            @Valid @ModelAttribute("nuevaCategoria") Categoria categoria,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "categoria";
        }

        categoriaRepository.save(categoria);
        return "redirect:/categoria";
    }

     @GetMapping("/editar/{id}")
    public String editarCategoria(@PathVariable int id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null) {
            return "redirect:/categoria";
        }

        model.addAttribute("categoria", categoria);
        return "categoria-editar";
    }

}
