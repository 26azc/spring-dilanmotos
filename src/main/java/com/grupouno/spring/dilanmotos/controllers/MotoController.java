package com.grupouno.spring.dilanmotos.controllers;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.grupouno.spring.dilanmotos.models.Moto;
import com.grupouno.spring.dilanmotos.repositories.MotoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
 

@Controller
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

 @GetMapping("/motos")
  public String mostrarMotos(@RequestParam(value = "search", required = false) String search, Model model) {
    List<Moto> motos = (search != null && !search.isEmpty())
         
            ? motoRepository.findByNombreContainingIgnoreCase(search)
            : motoRepository.findAll();

    model.addAttribute("motos", motos);
    model.addAttribute("nuevoMoto", new Moto());
    return "motos";    
    }

 @PostMapping("/motos")
    public String guardarMoto(@Valid @ModelAttribute("nuevoMoto") Moto moto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("moto", motoRepository.findAll());
            return "motos";
        }
        motoRepository.save(moto);
        return "redirect:/motos";
    }

}
