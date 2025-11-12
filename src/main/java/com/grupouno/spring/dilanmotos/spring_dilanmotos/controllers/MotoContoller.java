package com.grupouno.spring.dilanmotos.spring_dilanmotos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MotoContoller {

@GetMapping("/moto")
public String getMethodName(@RequestParam String param) {
    return new String();
}

    public String moto(){
        
        return "moto";
    }

}
