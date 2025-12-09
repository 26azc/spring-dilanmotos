package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.PQRS;
import com.grupouno.spring.dilanmotos.repositories.PqrsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la entidad {@link PQRS}.
 *
 * <p>Expone endpoints bajo la ruta base <b>/api</b> para interactuar con
 * los registros de PQRS en formato JSON. A diferencia del controlador
 * MVC tradicional, este controlador devuelve directamente objetos o listas
 * que son serializados automáticamente por Spring Boot.</p>
 *
 * <p>Endpoints principales:</p>
 * <ul>
 *   <li><b>GET /api/PQRS</b>: devuelve la lista completa de registros PQRS.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 * GET http://localhost:8080/api/PQRS
 * Response: [
 *   { "idPqrs": 1, "tipo": "Queja", "asunto": "Servicio", ... },
 *   { "idPqrs": 2, "tipo": "Petición", "asunto": "Garantía", ... }
 * ]
 * </pre>
 *
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class PqrsRestController {

    @Autowired
    private PqrsRepository pqrsRepository;

    /**
     * Devuelve todos los registros de PQRS en formato JSON.
     *
     * @return lista de objetos {@link PQRS}
     */
    @GetMapping("/PQRS")
    public List<PQRS> listarPQRS() {
        return pqrsRepository.findAll();
    }
}
