package com.grupouno.spring.dilanmotos.controllers;
import org.springframework.web.client.HttpClientErrorException;
import com.grupouno.spring.dilanmotos.controllers.GeminiService; // Asegúrate de importar tu servicio correctamente
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "*") // ¡Súper importante! Permite que tu React local se conecte sin errores de CORS
public class IAController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/consultar")
    public String consultarMotorIA(@RequestBody Map<String, String> requestBody) {
        // 1. Extraemos la información que nos envía React (o Postman)
        String modeloMoto = requestBody.get("modelo");
        String preguntaUsuario = requestBody.get("pregunta");

        // 2. Simulamos la búsqueda en Base de Datos (Más adelante esto será un llamado a tu BD real)
        String datosTecnicos = obtenerDatosDeBDMock(modeloMoto);

        // 3. Enviamos todo a nuestro Servicio de IA
        return geminiService.obtenerRecomendacion(preguntaUsuario, datosTecnicos);
    }

    // Método auxiliar temporal para simular tu base de datos
    private String obtenerDatosDeBDMock(String modelo) {
        if ("CFMoto 250NK".equalsIgnoreCase(modelo)) {
            return "Aceite recomendado: 10W-40. Llantas: Delantera 110/70-17, Trasera 140/60-17. Kit de arrastre: Paso 520.";
        }
        return "No hay información técnica disponible para este modelo en el inventario actual.";
    }
}