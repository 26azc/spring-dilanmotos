package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.PQRS;
import com.grupouno.spring.dilanmotos.repositories.PqrsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PqrsController {

    @Autowired
    private PqrsRepository pqrsRepository;

    @GetMapping("/pqrs")
    public String mostrarPqrs(
            @RequestParam(value = "search", required = false) String search,
            Model model
    ) {
        List<PQRS> pqrs = (search != null && !search.isEmpty())
                ? pqrsRepository.findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase(search, search)
                : pqrsRepository.findAll();

        model.addAttribute("pqrs", pqrs);
        model.addAttribute("nuevoPqrs", new PQRS());
        
        return "pqrs_menu"; 
    }
    
    // Guardar nueva pqrs
    @PostMapping("/pqrs")
    public String guardarPqrs(
            @Valid @NonNull @ModelAttribute("nuevoPqrs") PQRS pqrs,
            BindingResult result,
            Model model
    ) {
        // 1. Manejo de Errores de Campos de Usuario (Si falla, regresamos a la vista)
        if (result.hasErrors()) {
            model.addAttribute("pqrs", pqrsRepository.findAll()); 
            return "pqrs_menu"; 
        }

        // 2. Asignación de Valores del SISTEMA y por DEFECTO

        // 🎯 IMPORTANTE: Asignación de id_usuario. Reemplaza '1' con el ID real del usuario logueado.
        pqrs.setIdUsuario(1); 

        // El campo fecha_envio es asignado por el sistema.
        pqrs.setFecha(LocalDateTime.now());
        
        // Inicializa campos requeridos que el usuario no proporciona o que tienen un estado inicial.
        pqrs.setEstado("PENDIENTE"); 
        pqrs.setRespuesta_admin("Sin respuesta.");
        
        // Inicializa campos requeridos que se llenarán después
        pqrs.setCalificacion_servicio("-"); 
        pqrs.setComentario_servicio("-");
        
        // Deja fecha_respuesta como null
        pqrs.setFecha_respuesta(null); 

        // 3. Guardar en Base de Datos
        pqrsRepository.saveAndFlush(pqrs); 
        
        return "redirect:/pqrs?creado"; 
    }

    // Mostrar formulario de edición
    @GetMapping("/pqrs/editar/{id}")
    public String editarPqrs(@PathVariable("id") int id, Model model) {
        return pqrsRepository.findById(id)
                .map(pqrs -> {
                    model.addAttribute("pqrsEditada", pqrs);
                    return "editar_pqrs"; 
                })
                .orElse("redirect:/pqrs?error=not_found");
    }

    // Actualizar pqrs
    @PostMapping("/pqrs/actualizar")
    public String actualizarPqrs(@Valid @NonNull @ModelAttribute("pqrsEditada") PQRS pqrs, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_pqrs";
        }
        pqrsRepository.save(pqrs);
        return "redirect:/pqrs?actualizado";
    }

    // Eliminar pqrs
    @GetMapping("/pqrs/eliminar/{id}")
    public String eliminarPqrs(@PathVariable("id") int id) {
        if (pqrsRepository.existsById(id)) {
            pqrsRepository.deleteById(id);
            return "redirect:/pqrs?eliminado";
        }
        return "redirect:/pqrs?error=not_found";
    }
}