package com.grupouno.spring.dilanmotos.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MarcaRestController {


    @GetMapping("/marca2")
    
    public Map<String, Object> marca2() {
       Map<String, Object> respuesta = new HashMap<>();
       respuesta.put("titulo", "marca");
       respuesta.put("Servidor", "nombre del servidor");
       respuesta.put("Ip", "12345");
       return respuesta;

    }

}
