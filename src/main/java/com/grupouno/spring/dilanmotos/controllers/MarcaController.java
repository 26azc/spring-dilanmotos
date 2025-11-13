package com.grupouno.spring.dilanmotos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MarcaController {


    @GetMapping("/marca")
    
    public String marca(Model model) {

       model.addAttribute("titulo", "marca");
       model.addAttribute("Servidor", "nombre del servidor");
       model.addAttribute("Ip", "12345");
       return "marca";

    }

}
