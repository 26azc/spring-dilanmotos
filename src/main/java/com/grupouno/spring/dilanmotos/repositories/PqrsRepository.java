package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.PQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PqrsRepository extends JpaRepository<PQRS, Integer> {
    List<PQRS> findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase(String tipo, String asunto);
}
