package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Variables temporales para flujo de recuperación
    private String correoRecuperacion;
    private String codigoGenerado;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
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
        model.addAttribute("usuario", new Usuarios());
        return "register"; 
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

    // --- RECUPERAR CONTRASEÑA (PASO 1: INGRESAR CORREO) ---
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "Recuperar_Correo"; // vista donde el usuario ingresa su correo
    }

    @PostMapping("/forgot-password")
    public String sendCode(@RequestParam String correo, Model model) {
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByCorreo(correo);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "Correo no encontrado");
            return "Recuperar_Correo";
        }

        // Generar código simple (ejemplo)
        codigoGenerado = String.valueOf((int)(Math.random() * 900000) + 100000);
        correoRecuperacion = correo;

        // Aquí deberías enviar el código por correo electrónico al usuario
        System.out.println("Código enviado al correo: " + codigoGenerado);

        return "Ingresar_Codigo"; // vista para ingresar el código
    }

    // --- VERIFICAR CÓDIGO (PASO 2) ---
    @PostMapping("/verify-code")
    public String verifyCode(@RequestParam String codigo, Model model) {
        if (!codigo.equals(codigoGenerado)) {
            model.addAttribute("error", "Código inválido");
            return "IngresarCodigo";
        }
        return "Crear_Contraseña"; // pasa a la vista de nueva contraseña
    }

    // --- CREAR NUEVA CONTRASEÑA (PASO 3) ---
    @GetMapping("/reset-password")
    public String resetPasswordForm() {
        return "Crear_Contraseña";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String nuevaContrasena,
                                @RequestParam String confirmarContrasena,
                                Model model) {
        if (!nuevaContrasena.equals(confirmarContrasena)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "Crear_Contraseña";
        }

        // Actualizar contraseña en BD
        Optional<Usuarios> usuarioOpt = usuarioRepository.findByCorreo(correoRecuperacion);
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
            usuarioRepository.save(usuario);
        }

        // Limpiar variables temporales
        correoRecuperacion = null;
        codigoGenerado = null;

        return "redirect:/login"; 
    }

    // --- OTRAS VISTAS ---
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; 
    }

    @GetMapping("/logout-page")
    public String logoutPage() {
        return "logout"; 
    }

    @GetMapping("/ComunicacionTec")
    public String comunicacionTec() {
        return "ComunicacionTec"; 
    }

    @GetMapping("/recomendacion")
    public String recomendacion() {
        return "recomendacion"; 
    }

    @GetMapping("/CuentaUsuario")
    public String CuentaUsuario() {
        return "CuentaUsuario"; 
    }

    @GetMapping("/EditarInfoMoto")
    public String EditarInfoMoto() {
        return "EditarInfoMoto"; 
    }
}
