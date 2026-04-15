package com.grupouno.spring.dilanmotos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "*") // Permite peticiones desde tu frontend de React
public class IAController {

    @Autowired
    private GroqService groqService;

    /**
     * Endpoint para consultar la IA de Groq enviando motor, falla y el historial.
     * @param payload Mapa con los datos provenientes del frontend.
     * @return Respuesta con la recomendación generada por la IA.
     */
    @PostMapping("/consultar")
    public ResponseEntity<Map<String, String>> consultarIA(@RequestBody Map<String, Object> payload) {
        Map<String, String> respuestaFinal = new HashMap<>();
        
        try {
            // 1. Extraer y validar los datos del JSON
            String motor = payload.get("motor") != null ? payload.get("motor").toString() : "No especificado";
            String falla = payload.get("falla") != null ? payload.get("falla").toString() : "No especificada";
            
            // Casting seguro para el historial
            List<Map<String, Object>> historial = (List<Map<String, Object>>) payload.get("historial");
            
            if (historial == null) {
                historial = List.of(); // Si no hay historial, mandamos una lista vacía
            }

            // 2. Llamar al servicio GroqService con la firma de la Opción B (3 argumentos)
            // Esto resuelve el error de "Unresolved compilation problem"
            String recomendacion = groqService.obtenerRecomendacion(historial, motor, falla);

            // 3. Preparar la respuesta exitosa
            respuestaFinal.put("recomendacion", recomendacion);
            return ResponseEntity.ok(respuestaFinal);

        } catch (ClassCastException e) {
            respuestaFinal.put("error", "Error en el formato de los datos enviados: " + e.getMessage());
            return ResponseEntity.status(400).body(respuestaFinal);
        } catch (Exception e) {
            respuestaFinal.put("error", "Error interno en el servidor: " + e.getMessage());
            return ResponseEntity.status(500).body(respuestaFinal);
        }
    }
}