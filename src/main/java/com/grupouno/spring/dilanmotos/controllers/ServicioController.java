package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Servicio;
import com.grupouno.spring.dilanmotos.repositories.ServicioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    // 📋 Listar servicios con búsqueda
    @GetMapping
    public String listarServicios(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Servicio> servicios = (search != null && !search.isEmpty())
                ? servicioRepository.findByComentarioContainingIgnoreCaseOrEstadoServicioContainingIgnoreCase(search, search)
                : servicioRepository.findAll();

        model.addAttribute("servicios", servicios);
        model.addAttribute("nuevoServicio", new Servicio());
        return "servicio";
    }

    // ➕ Crear servicio
    @PostMapping
    public String guardarServicio(@Valid @ModelAttribute("nuevoServicio") Servicio servicio,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {
        if (result.hasErrors()) {
            model.addAttribute("servicios", servicioRepository.findAll());
            return "servicio";
        }
        servicioRepository.save(servicio);
        redirectAttributes.addFlashAttribute("creado", true);
        return "redirect:/servicio";
    }

    // ✏️ Editar servicio
    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable("id") Integer id, Model model) {
        return servicioRepository.findById(id)
                .map(s -> {
                    model.addAttribute("servicioEditado", s);
                    return "editar_servicio";
                })
                .orElse("redirect:/servicio");
    }

    // 🔄 Actualizar servicio
    @PostMapping("/actualizar")
    public String actualizarServicio(@Valid @ModelAttribute("servicioEditado") Servicio servicio,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editar_servicio";
        }
        servicioRepository.save(servicio);
        redirectAttributes.addFlashAttribute("actualizado", true);
        return "redirect:/servicio";
    }

    // 🗑 Eliminar servicio
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        servicioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("eliminado", true);
        return "redirect:/servicio";
    }
}
