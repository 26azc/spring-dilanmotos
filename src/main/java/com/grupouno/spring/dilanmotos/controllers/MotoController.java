package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.models.Usuarios;
import com.grupouno.spring.dilanmotos.models.Marca;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MotoController {

    private final MotoRepository motoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MarcaRepository marcaRepository;

    public MotoController(MotoRepository motoRepository,
                          UsuarioRepository usuarioRepository,
                          MarcaRepository marcaRepository) {
        this.motoRepository = motoRepository;
        this.usuarioRepository = usuarioRepository;
        this.marcaRepository = marcaRepository;
    }

    // --- LISTADO DE MOTOS ---
    @GetMapping("/moto")
    public String listarMotos(Model model) {
        List<Moto> motos = motoRepository.findAll();
        model.addAttribute("motos", motos);

        // Objeto vacío para formulario
        model.addAttribute("nuevaMoto", new Moto());

        // Lista de marcas
        List<Marca> marcas = marcaRepository.findAll();
        model.addAttribute("marcas", marcas);

        return "moto";
    }

    // --- BUSCAR POR MODELO ---
    @GetMapping("/moto/buscar")
    public String buscarPorModelo(@RequestParam("modelo") String modelo, Model model, Authentication auth) {
        String correo = auth.getName();
        Usuarios usuario = usuarioRepository.findByCorreo(correo).orElseThrow();

        List<Moto> motos = motoRepository.findByModeloContainingIgnoreCase(modelo).stream()
                .filter(m -> m.getUsuario().getIdUsuario() == usuario.getIdUsuario())
                .toList();

        model.addAttribute("motos", motos);
        model.addAttribute("nuevaMoto", new Moto());
        model.addAttribute("marcas", marcaRepository.findAll());

        return "moto";
    }

    // --- REGISTRAR MOTO ---
    @PostMapping("/moto")
    public String registrarMoto(@ModelAttribute("nuevaMoto") Moto nuevaMoto,
                                @RequestParam("idMarca") Integer idMarca,
                                Authentication auth) {
        String correo = auth.getName();
        Usuarios usuario = usuarioRepository.findByCorreo(correo).orElseThrow();

        // Setear usuario y marca
        nuevaMoto.setUsuario(usuario);
        Marca marca = marcaRepository.findById(idMarca).orElseThrow();
        nuevaMoto.setMarca(marca);

        motoRepository.save(nuevaMoto);

        return "redirect:/moto?creado=true";
    }

    // --- EDITAR MOTO ---
    @GetMapping("/moto/editar/{id}")
    public String editarMoto(@PathVariable("id") Integer idMoto, Model model) {
        Moto moto = motoRepository.findById(idMoto).orElseThrow();

        model.addAttribute("motoEditada", moto);
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("marcas", marcaRepository.findAll());

        return "editar_moto";
    }

    @PostMapping("/moto/actualizar")
    public String actualizarMoto(@ModelAttribute("motoEditada") Moto motoEditada,
                                 @RequestParam("idMarca") Integer idMarca) {
        Marca marca = marcaRepository.findById(idMarca).orElseThrow();
        motoEditada.setMarca(marca);

        motoRepository.save(motoEditada);
        return "redirect:/moto?actualizado=true";
    }

    // --- ELIMINAR MOTO ---
    @GetMapping("/moto/eliminar/{id}")
    public String eliminarMoto(@PathVariable("id") Integer idMoto) {
        motoRepository.deleteById(idMoto);
        return "redirect:/moto?eliminado=true";
    }
}