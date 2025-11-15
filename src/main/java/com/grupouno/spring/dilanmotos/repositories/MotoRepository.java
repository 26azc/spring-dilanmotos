package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Moto;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface MotoRepository extends JpaRepository<Moto, Integer> {
    List<Moto> findByNombreContainingIgnoreCase(String nombre);
}
