package com.grupouno.spring.dilanmotos.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;


@Service
public class GeminiService {

    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerRecomendacion(String consultaUsuario, String datosTecnicos) {
        String url = apiUrl + "?key=" + apiKey;

        // Construimos el "System Prompt" con los datos de tu base de datos
        String promptCompleto = "Actúa como un experto mecánico de motos. " +
                "Contexto técnico del modelo: " + datosTecnicos + ". " +
                "Pregunta del cliente: " + consultaUsuario;

        // Estructura simplificada del JSON para Gemini
        Map<String, Object> body = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(Map.of("text", promptCompleto)))
            )
        );

try {
    // 1. Pedimos la respuesta como un árbol JSON (JsonNode)
    ResponseEntity<JsonNode> response = restTemplate.postForEntity(url, body, JsonNode.class);
    
    // 2. Navegamos exactamente hasta el texto que nos interesa dentro de la respuesta de Google
    String textoGenerado = response.getBody()
            .at("/candidates/0/content/parts/0/text")
            .asText();
            
    return textoGenerado; 

} catch (org.springframework.web.client.HttpClientErrorException e) {
    return "Google rechazó la petición: " + e.getResponseBodyAsString();
} catch (Exception e) {
    return "Error interno en el servidor: " + e.getMessage();
}
    }
}