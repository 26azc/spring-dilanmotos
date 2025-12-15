package com.grupouno.spring.dilanmotos.controllers;

import com.grupouno.spring.dilanmotos.models.Productos;
import com.grupouno.spring.dilanmotos.repositories.ProductosRepository;
import com.grupouno.spring.dilanmotos.repositories.CategoriaRepository;
import com.grupouno.spring.dilanmotos.repositories.MarcaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductosController {

    @Autowired private ProductosRepository productosRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private MarcaRepository marcaRepository;

    // ---------------- CRUD GENERAL ----------------

    // Listar productos
    @GetMapping("/productos")
    public String mostrarProducto(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Productos> resultados = (search != null && !search.isEmpty())
            ? productosRepository.findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(search, search)
            : productosRepository.findAll();

        model.addAttribute("productos", resultados);
        model.addAttribute("nuevoProducto", new Productos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("marcas", marcaRepository.findAll());
        return "productos";
    }

    // Guardar producto
    @PostMapping("/productos/guardar")
    public String guardarProducto(@Valid @NonNull @ModelAttribute("nuevoProducto") Productos producto,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productos", productosRepository.findAll());
            model.addAttribute("categorias", categoriaRepository.findAll());
            model.addAttribute("marcas", marcaRepository.findAll());
            return "productos";
        }
        productosRepository.save(producto);
        return "redirect:/productos?creado";
    }

    // Editar producto
    @GetMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable("id") int id, Model model) {
        return productosRepository.findById(id)
            .map(producto -> {
                model.addAttribute("productoEditada", producto);
                model.addAttribute("categorias", categoriaRepository.findAll());
                model.addAttribute("marcas", marcaRepository.findAll());
                return "editar_productos";
            })
            .orElse("redirect:/productos?error=not_found");
    }

    // Actualizar producto
    @PostMapping("/productos/actualizar")
    public String actualizarProducto(@Valid @NonNull @ModelAttribute("productoEditada") Productos producto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "editar_productos";
        }
        productosRepository.save(producto);
        return "redirect:/productos?actualizado";
    }

    // Eliminar producto
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") int id) {
        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
            return "redirect:/productos?eliminado";
        }
        return "redirect:/productos?error=not_found";
    }

    // ---------------- NUEVAS RUTAS DE CATÁLOGO ----------------

    // Catálogo Kit de Arrastre
    @GetMapping("/CatalogoKitArrastreAutenticado")
    public String catalogoKitArrastre(@RequestParam(required = false) String marca, Model model) {
        String categoria = "Kit de arrastre";

        List<Productos> productos = (marca == null || marca.equals("Todas"))
            ? productosRepository.findByCategoriaNombre(categoria)
            : productosRepository.findByCategoriaNombreAndMarcaNombre(categoria, marca);

        model.addAttribute("productos", productos);
        model.addAttribute("marcaSeleccionada", marca != null ? marca : "Todas");

        return "CatalogoKitArrastreAutenticado";
    }

    // Catálogo Aceites
    @GetMapping("/CatalogoAceiteAutenticado")
    public String catalogoAceite(@RequestParam(required = false) String marca, Model model) {
        String categoria = "Aceites";

        List<Productos> productos = (marca == null || marca.equals("Todas"))
            ? productosRepository.findByCategoriaNombre(categoria)
            : productosRepository.findByCategoriaNombreAndMarcaNombre(categoria, marca);

        model.addAttribute("productos", productos);
        model.addAttribute("marcaSeleccionada", marca != null ? marca : "Todas");

        return "CatalogoAceiteAutenticado";
    }

    // Catálogo Llantas
  @GetMapping("/CatalogoLlantaAutenticado")
public String catalogoLlanta(@RequestParam(required = false) String marca, Model model) {
    String categoria = "Llantas";
    List<Productos> productos;

    if (marca == null || marca.equalsIgnoreCase("Todas")) {
        // Mostrar todos los productos de la categoría "Llantas"
        productos = productosRepository.findByCategoriaNombre(categoria);
    } else {
        // Buscar productos cuyo nombre contenga la marca (aunque no esté en tabla Marca)
        productos = productosRepository.findByCategoriaNombreAndNombreContainingIgnoreCase(categoria, marca);
    }

    model.addAttribute("productos", productos);
    model.addAttribute("marcaSeleccionada", marca != null ? marca : "Todas");

    return "CatalogoLlantaAutenticado";
}
    }
