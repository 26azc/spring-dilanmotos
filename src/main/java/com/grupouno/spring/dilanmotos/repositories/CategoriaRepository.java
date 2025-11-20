package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
}
