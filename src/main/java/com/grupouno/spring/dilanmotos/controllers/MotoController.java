
package com.grupouno.spring.dilanmotos.controllers;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;

import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import com.grupouno.spring.dilanmotos.repositories.UsuarioRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.grupouno.spring.dilanmotos.models.Usuarios; 
import com.grupouno.spring.dilanmotos.models.Marca;

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository; 

    @Autowired
    private MarcaRepository marcaRepository;

    // Mostrar listado y formulario
    @GetMapping("/moto")
    public String mostrarMotos(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Moto> motos = (search != null && !search.isEmpty())
            ? motoRepository.findByModeloContainingIgnoreCase(search)
            : motoRepository.findAll();

        model.addAttribute("motos", motos); 
        model.addAttribute("nuevaMoto", new Moto());
    
        List<Usuarios> usuarios = usuarioRepository.findAll();
        List<Marca> marcas = marcaRepository.findAll();

        model.addAttribute("usuarios", usuarios); 
        model.addAttribute("marcas", marcas); 
        
        return "moto";
    }

    // Guardar nueva moto
    @PostMapping("/moto")
    public String guardarMoto(
        @Valid @NonNull @ModelAttribute("nuevaMoto") Moto moto,
        BindingResult result,
        Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("moto", motoRepository.findAll());
            return "moto";
        }

        motoRepository.save(moto);
        return "redirect:/moto?creado";
    }

 @GetMapping("/moto/editar/{id}")
    public String editarMoto(@PathVariable("id") int id, Model model) {
        Moto moto = motoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Moto no encontrada: " + id));
        model.addAttribute("motoEditada", moto);

        List<Usuarios> usuarios = usuarioRepository.findAll();
        List<Marca> marcas = marcaRepository.findAll();
        
        model.addAttribute("usuarios", usuarios); 
        model.addAttribute("marcas", marcas);
        return "editar_moto";
    }

 @PostMapping("/moto/actualizar")
    public String actualizarMoto(@Valid @NonNull @ModelAttribute("motoEditada") Moto moto, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_moto";
        }
        motoRepository.save(moto);
        return "redirect:/moto";
    }

 @GetMapping("/moto/eliminar/{id}")
    public String eliminarMoto(@PathVariable("id") int id) {
        motoRepository.deleteById(id);
        return "redirect:/moto";
    }
}
