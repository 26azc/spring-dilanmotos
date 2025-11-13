package com.grupouno.spring.dilanmotos.spring_dilanmotos.controllers;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.spring_dilanmotos.models.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UsuarioRestController {
//http://localhost:8080/usuario

    public String Usuario; 

    Usuarios user1 = new Usuarios("Juan Andres", "juan@gmail.com", "flow123", 1);

    @GetMapping(path = "/usuario2")
    public Map<String, Object> usuario2(){

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Usuario", user1);
       
        
        return respuesta;
    }


    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<Usuarios> listarUsuarios() {
        return usuarioRepository.findAll();
    }


}
