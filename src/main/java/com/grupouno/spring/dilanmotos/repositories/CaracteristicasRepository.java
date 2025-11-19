package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Caracteristicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicasRepository extends JpaRepository<Caracteristicas, Integer> {
    List<Caracteristicas> findByDescripcionContainingIgnoreCase(String descripcion);
}