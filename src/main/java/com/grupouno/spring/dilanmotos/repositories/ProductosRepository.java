package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {

    // Buscar productos por coincidencia en nombre o descripción
    List<Productos> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

    // Buscar productos por nombre de categoría
    List<Productos> findByCategoriaNombre(String nombreCategoria);

    List<Productos> findByCategoriaNombreAndNombreContainingIgnoreCase(String categoria, String nombre);
    // Buscar productos por nombre de categoría y nombre de marca
    List<Productos> findByCategoriaNombreAndMarcaNombre(String nombreCategoria, String nombreMarca);


}