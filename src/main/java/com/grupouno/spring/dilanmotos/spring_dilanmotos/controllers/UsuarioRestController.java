package com.grupouno.spring.dilanmotos.spring_dilanmotos.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.grupouno.spring.dilanmotos.spring_dilanmotos.models.Usuarios;
import java.util.HashMap;
import java.util.Map;




/*
 * Maurito el mas capito frifai 123
 */


@RestController
@RequestMapping("/api")
public class UsuarioRestController {
//http://localhost:8080/usuario

    Usuarios user1 = new Usuarios( "Juan Andres "," juan@gmail.com", "flow123", 1);

    @GetMapping(path = "/usuario2")
    public Map<String, Object> usuario2(){

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Usuario", user1);
       
        
        return respuesta;
    }
}
