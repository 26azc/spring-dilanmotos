package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.services.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar listado y formulario
    @GetMapping("/usuario")
    public String mostrarUsuarios(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Usuarios> usuarios = (search != null && !search.isEmpty())
            ? usuarioRepository.findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(search, search)
            : usuarioRepository.findAll();

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nuevoUsuario", new Usuarios());
        return "usuario";
    }

    // Guardar nuevo usuario
    @PostMapping("/usuario")
    public String guardarUsuario(
        @Valid @NonNull @ModelAttribute("nuevoUsuario") Usuarios usuario,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios", usuarioRepository.findAll());
            return "usuario";
        }

        usuarioRepository.save(usuario);
        return "redirect:/usuario?creado";
    }

    // Mostrar formulario de edición
    @GetMapping("/usuario/editar/{id}")
    public String editarUsuario(@PathVariable("id") int id, Model model) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                model.addAttribute("usuarioEditado", usuario);
                return "editar_usuario";
            })
            .orElse("redirect:/usuario?error=not_found");
    }

    // Actualizar usuario
    @PostMapping("/usuario/actualizar")
    public String actualizarUsuario(
        @Valid @NonNull @ModelAttribute("usuarioEditado") Usuarios usuario,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "editar_usuario";
        }

        usuarioRepository.save(usuario);
        return "redirect:/usuario?actualizado";
    }

    // Eliminar usuario
    @GetMapping("/usuario/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return "redirect:/usuario?eliminado";
        }
        return "redirect:/usuario?error=not_found";
    }
    private final UsuarioService usuarioService;

    // Constructor correcto: parámetro con nombre y asignación
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Perfil/Cuenta del usuario autenticado
    @GetMapping("/CuentaUsuario")
    public String miCuenta(@AuthenticationPrincipal User principal, Model model) {
        // principal.getUsername() devuelve el "username" con el que autenticó (idealmente tu correo)
        String correo = principal.getUsername();

        // Llama al servicio correctamente
        Usuarios usuarioActual = usuarioService.getByCorreo(correo);

        // Pasa el objeto a la vista
        model.addAttribute("usuario", usuarioActual);
        return "CuentaUsuario";
    }
}
