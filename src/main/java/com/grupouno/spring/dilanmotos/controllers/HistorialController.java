package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Historial;
import com.grupouno.spring.dilanmotos.repositories.HistorialRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/historial")
public class HistorialController {

    private final HistorialRepository historialRepository;

    public HistorialController(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    @GetMapping
    public String mostrarHistorial(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Historial> historiales = (search != null && !search.isEmpty())
                ? historialRepository.findByAccionContainingIgnoreCaseOrDetalleContainingIgnoreCase(search, search)
                : historialRepository.findAll();

        model.addAttribute("historiales", historiales);
        model.addAttribute("nuevoHistorial", new Historial());
        return "historial";
    }

    @PostMapping
    public String guardarHistorial(@Valid @ModelAttribute("nuevoHistorial") Historial historial,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("historiales", historialRepository.findAll());
            return "historial";
        }

        if (historial.getFecha() == null) {
            historial.setFecha(LocalDateTime.now());
        }

        historialRepository.save(historial);
        redirectAttributes.addAttribute("creado", true);
        return "redirect:/historial";
    }

    @GetMapping("/editar/{id}")
    public String editarHistorial(@PathVariable("id") Integer id, Model model) {
        Optional<Historial> historialOpt = historialRepository.findById(id);
        if (historialOpt.isEmpty()) {
            return "redirect:/historial";
        }
        model.addAttribute("historialEditado", historialOpt.get());
        return "editar_historial";
    }

    @PostMapping("/actualizar")
    public String actualizarHistorial(@Valid @ModelAttribute("historialEditado") Historial historial,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editar_historial";
        }

        historialRepository.save(historial);
        redirectAttributes.addAttribute("actualizado", true);
        return "redirect:/historial";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarHistorial(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (historialRepository.existsById(id)) {
            historialRepository.deleteById(id);
            redirectAttributes.addAttribute("eliminado", true);
        }
        return "redirect:/historial";
    }
}
