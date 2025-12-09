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

/**
 * Controlador para gestionar las operaciones relacionadas con PQRS
 * (Peticiones, Quejas, Reclamos y Sugerencias).
 *
 * <p>Este controlador expone endpoints para listar, crear, editar,
 * actualizar y eliminar registros de PQRS. Se integra con la capa
 * de repositorio {@link PqrsRepository} para interactuar con la base de datos.</p>
 *
 * <p>Vistas utilizadas:</p>
 * <ul>
 *   <li><b>pqrs_menu</b>: muestra el listado de PQRS y el formulario de creación.</li>
 *   <li><b>editar_pqrs</b>: formulario para editar un PQRS existente.</li>
 * </ul>
 *
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */
@Controller
public class PqrsController {

    @Autowired
    private PqrsRepository pqrsRepository;

    /**
     * Muestra el listado de PQRS.
     * Si se recibe un parámetro de búsqueda, filtra por tipo o asunto.
     *
     * @param search texto opcional para filtrar PQRS
     * @param model  modelo para pasar datos a la vista
     * @return nombre de la vista "pqrs_menu"
     */
    @GetMapping("/pqrs")
    public String mostrarPqrs(@RequestParam(value = "search", required = false) String search, Model model) {
        List<PQRS> pqrs = (search != null && !search.isEmpty())
                ? pqrsRepository.findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase(search, search)
                : pqrsRepository.findAll();

        model.addAttribute("pqrs", pqrs);
        model.addAttribute("nuevoPqrs", new PQRS());
        return "pqrs_menu";
    }

    /**
     * Guarda un nuevo PQRS en la base de datos.
     * Si hay errores de validación, retorna al formulario.
     *
     * @param pqrs   objeto PQRS recibido del formulario
     * @param result resultado de la validación
     * @param model  modelo para pasar datos a la vista
     * @return redirección a "/pqrs" con parámetro de estado
     */
    @PostMapping("/pqrs")
    public String guardarPqrs(@Valid @NonNull @ModelAttribute("nuevoPqrs") PQRS pqrs, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pqrs", pqrsRepository.findAll());
            return "pqrs_menu";
        }

        // Valores iniciales por defecto
        pqrs.setIdUsuario(1); // TODO: reemplazar con ID real del usuario autenticado
        pqrs.setFecha(LocalDateTime.now());
        pqrs.setEstado("PENDIENTE");
        pqrs.setRespuesta_admin("Sin respuesta.");
        pqrs.setCalificacion_servicio("-");
        pqrs.setComentario_servicio("-");
        pqrs.setFecha_respuesta(null);

        pqrsRepository.save(pqrs);
        return "redirect:/pqrs?creado";
    }

    /**
     * Carga un PQRS existente para edición.
     *
     * @param id    identificador del PQRS
     * @param model modelo para pasar datos a la vista
     * @return vista "editar_pqrs" si existe, redirección si no se encuentra
     */
    @GetMapping("/pqrs/editar/{id}")
    public String editarPqrs(@PathVariable("id") int id, Model model) {
        return pqrsRepository.findById(id)
                .map(pqrs -> {
                    model.addAttribute("pqrsEditada", pqrs);
                    return "editar_pqrs";
                })
                .orElse("redirect:/pqrs?error=not_found");
    }

    /**
     * Actualiza un PQRS existente en la base de datos.
     *
     * @param pqrs   objeto PQRS editado
     * @param result resultado de la validación
     * @return redirección a "/pqrs" con parámetro de estado
     */
    @PostMapping("/pqrs/actualizar")
    public String actualizarPqrs(@Valid @NonNull @ModelAttribute("pqrsEditada") PQRS pqrs, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_pqrs";
        }
        pqrsRepository.save(pqrs);
        return "redirect:/pqrs?actualizado";
    }

    /**
     * Elimina un PQRS por su identificador.
     *
     * @param id identificador del PQRS
     * @return redirección a "/pqrs" con parámetro de estado
     */
    @GetMapping("/pqrs/eliminar/{id}")
    public String eliminarPqrs(@PathVariable("id") int id) {
        if (pqrsRepository.existsById(id)) {
            pqrsRepository.deleteById(id);
            return "redirect:/pqrs?eliminado";
        }
        return "redirect:/pqrs?error=not_found";
    }
}
