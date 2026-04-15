package com.grupouno.spring.dilanmotos.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final String URL_API = "https://api.groq.com/openai/v1/chat/completions";

    public String obtenerRecomendacion(List<Map<String, Object>> historial, String motor, String falla) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Reconstruir la conversación previa para darle memoria a la IA
        StringBuilder contextoChat = new StringBuilder();
        if (historial != null) {
            for (Map<String, Object> msg : historial) {
                String rol = msg.get("rol").toString();
                String texto = msg.get("texto").toString();
                contextoChat.append(rol.equalsIgnoreCase("usuario") ? "Cliente: " : "Mecánico: ")
                            .append(texto).append("\n");
            }
        }

        // 2. Configurar el Prompt con la memoria integrada
        String prompt = String.format(
    "Eres el mecánico jefe de Dilan Motos. Si vas a dar precios o comparar repuestos, " +
    "USA TABLAS DE MARKDOWN para que se vea organizado. \n" +
    "Ejemplo de formato:\n" +
    "| Repuesto | Gama Alta | Gama Económica |\n" +
    "| :--- | :--- | :--- |\n" +
    "Recuerda que en dilanmotos solo tenemos aceites, llantas y kits de arrastres" +
    "| Aceite | $50.000 | $20.000 |\n\n" +
    "Contexto: %s, Moto: %s, Pregunta: %s",
    contextoChat, motor, falla
);

        // 3. Preparar JSON para Groq
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.3-70b-versatile");
        body.put("messages", Collections.singletonList(message));
        body.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(URL_API, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> messageResult = (Map<String, Object>) choices.get(0).get("message");
            return (String) messageResult.get("content");
        } catch (Exception e) {
            return "🚨 Error en los fierros de la IA: " + e.getMessage();
        }
    }
}