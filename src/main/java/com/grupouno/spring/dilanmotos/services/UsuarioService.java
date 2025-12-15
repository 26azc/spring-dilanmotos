package com.grupouno.spring.dilanmotos.services;

import org.springframework.stereotype.Service;
import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;


 /*
 logica para obtener los usuarios y guardarlos
 separando las responsabilidades con la base de datos */
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public Usuarios getByCorreo(String correo){
        return usuarioRepository.findByCorreo(correo)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    public Usuarios guardar(Usuarios usuario){
        return usuarioRepository.save(usuario);
    }
    
}
