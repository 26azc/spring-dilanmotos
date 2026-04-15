package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {
@Query(value = "SELECT p.* FROM productos p " +
                   "INNER JOIN marca m ON p.id_marca = m.id_marca " + 
                   "WHERE p.nombre LIKE CONCAT('%', :busqueda, '%') " +
                   "OR p.descripcion LIKE CONCAT('%', :busqueda, '%') " +
                   "OR m.nombre LIKE CONCAT('%', :busqueda, '%')", // <-- Ajuste aquí el nombre de la columna si es diferente
           nativeQuery = true)
    List<Productos> buscarTodoRelacionado(@Param("busqueda") String busqueda);
}