package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Referencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReferenciaRepository extends JpaRepository<Referencia, Integer> {

    List<Referencia> findByMarca_IdMarca(Integer idMarca);
    List<Referencia> findByNombreContainingIgnoreCase(String nombre);
}