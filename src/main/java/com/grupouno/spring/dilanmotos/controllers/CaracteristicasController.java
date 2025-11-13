package com.grupouno.spring.dilanmotos.controllers;

public class CaracteristicasController {

}
/*
 * 
 * package com.grupouno.spring.dilanmotos.spring_dilanmotos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupouno.spring.dilanmotos.spring_dilanmotos.models.Usuarios;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Arrays; // añadido

@Controller
public class UsuarioController {
//http://localhost:8080/usuario
   @GetMapping("/usuario")
public String Usuario(Model model){
    List<Usuarios> lista = Arrays.asList(
        new Usuarios("Juan Andres", "juan@gmail.com", "flow123", 1),
        new Usuarios("Ana María", "ana@gmail.com", "clave456", 2)
    );
    model.addAttribute("usuarios", lista);
    return "usuario";
    }
}

 */