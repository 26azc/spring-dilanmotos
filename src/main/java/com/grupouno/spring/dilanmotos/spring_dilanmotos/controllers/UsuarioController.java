package com.grupouno.spring.dilanmotos.spring_dilanmotos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupouno.spring.dilanmotos.spring_dilanmotos.models.Usuarios;
import org.springframework.ui.Model;

@Controller
public class UsuarioController {
//http://localhost:8080/usuario
    @GetMapping("/usuario")

    
    public String Usuario(Model model){
        Usuarios user1 = new Usuarios( "Juan Andres ",
        " juan@gmail.com", "flow123", 1);
        
        model.addAttribute("usuario", user1);
        
        return "usuario";
    }
}
