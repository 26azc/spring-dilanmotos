package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Categoria;
import com.grupouno.spring.dilanmotos.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
