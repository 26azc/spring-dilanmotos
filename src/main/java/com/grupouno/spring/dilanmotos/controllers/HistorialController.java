                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Historial;
import com.grupouno.spring.dilanmotos.repositories.HistorialRepository;
import org.springframework.lang.NonNull;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String guardarHistorial(@Valid @ModelAttribute("nuevoHistorial") Historial historial,RedirectAttributes redirectAttributes,
                                   BindingResult result, Model model) {
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
    public String editarHistorial(@NonNull @PathVariable("id") Integer id, Model model) {
    Optional<Historial> historialOpt = historialRepository.findById(id);
        if (historialOpt.isEmpty()) {
            return "redirect:/historial";
        }
        model.addAttribute("historialEditado", historialOpt.get());
        return "editar_historial";
    }

    @PostMapping("/actualizar")
    public String actualizarHistorial(@NonNull @Valid @ModelAttribute("historialEditado") Historial historial,RedirectAttributes redirectAttributes,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "editar_historial";
        }

        historialRepository.save(historial);
        redirectAttributes.addAttribute("actualizado", true);
        return "redirect:/historial";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarHistorial(@NonNull @PathVariable("id") Integer id) {
       historialRepository.deleteById(id);
       return "redirect:/historial";
    }
}
