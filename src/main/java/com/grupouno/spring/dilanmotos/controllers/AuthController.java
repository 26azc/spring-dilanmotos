package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "register"; // register.html
    }

    @PostMapping("/register")
   public String processRegister(@ModelAttribute Usuarios usuario, Model model) {
    if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
        model.addAttribute("error", "El correo ya está registrado.");
        return "register";
    }

    usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
    usuario.setRol("USER");
    usuario.setHabilitado(true);
    usuarioRepository.save(usuario);
    return "redirect:/login";
}

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // dashboard.html
    }

    @GetMapping("/logout-page")
public String logoutPage() {
    return "logout"; // logout.html
}

}
