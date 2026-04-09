package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
}