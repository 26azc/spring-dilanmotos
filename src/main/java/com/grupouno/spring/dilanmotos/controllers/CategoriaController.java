package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Categoria;
import com.grupouno.spring.dilanmotos.repositories.CategoriaRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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

     @PostMapping("/categoria")
    public String guardarCategoria(
        @Valid @NonNull @ModelAttribute("nuevaCategoria") Categoria categoria,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "categoria";
        }

        categoriaRepository.save(categoria);
        return "redirect:/categoria?creado";
    }

     @GetMapping("/categoria/editar/{id}")
    public String editarCategoria(@PathVariable int id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        if (categoria == null) {
            return "redirect:/categoria";
        }

        model.addAttribute("categoria", categoria);
        return "categoria-editar";
    }


    //Actualizar las categorías
    @PostMapping("/categoria/actualizar")
    public String actualizarCategoria(
        @Valid @NonNull @ModelAttribute("categoriaEditada") Categoria categoria,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "editar_categoria";
        }

        categoriaRepository.save(categoria);
        return "redirect:/categoria?actualizado";
    }

    // Eliminar categoría
    @GetMapping("/categoria/eliminar/{id}")
    public String eliminarCategoria(@PathVariable("id") int id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return "redirect:/categoria?eliminado";
        }
        return "redirect:/categoria?error=not_found";
    }


}
