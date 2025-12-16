package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controlador de autenticación y registro.
 *
 * <p>Gestiona el login, registro de usuarios y recuperación de contraseña.</p>
 *
 * <p>En el registro, además de los datos del usuario, se permite registrar
 * la primera moto asociada al usuario.</p>
 *
 * @author Juan
 * @version 1.1
 */
@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final MarcaRepository marcaRepository;
    private final PasswordEncoder passwordEncoder;

    // Variables temporales para recuperación de contraseña
    

    public AuthController(UsuarioRepository usuarioRepository,
                          MarcaRepository marcaRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.marcaRepository = marcaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // --- LOGIN ---
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // --- REGISTRO ---
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        Usuarios usuario = new Usuarios();
        usuario.getMotos().add(new Moto()); // inicializamos una moto vacía

        model.addAttribute("usuario", usuario);

        // cargar marcas desde la BD
        List<Marca> marcas = marcaRepository.findAll();
        model.addAttribute("marcas", marcas);

        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("usuario") Usuarios usuario, Model model) {
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado.");
            return "register";
        }

        try {
            // Encriptar contraseña
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            usuario.setRol("USER");
            usuario.setHabilitado(true);

            // Relacionar la moto con el usuario
            if (usuario.getMotos() != null) {
                usuario.getMotos().forEach(moto -> moto.setUsuario(usuario));
            }

            // Guardar usuario + moto
            usuarioRepository.save(usuario);

        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
            return "register";
        }

        return "redirect:/login";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
    return "dashboard"; 
}



}