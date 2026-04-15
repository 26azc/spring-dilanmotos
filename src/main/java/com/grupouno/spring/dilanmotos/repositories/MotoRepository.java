package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer> {
    
    // Para buscar modelos por marca (el que faltaba)
   List<Moto> findByMarca_IdMarca(Integer idMarca);
    
    // Para el chat de IA
    Optional<Moto> findByUsuario_IdUsuario(Integer idUsuario);
    
    List<Moto> findByModeloContainingIgnoreCase(String modelo);
}