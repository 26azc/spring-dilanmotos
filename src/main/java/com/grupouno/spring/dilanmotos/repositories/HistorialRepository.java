package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    List<Historial> findByAccionContainingIgnoreCaseOrDetalleContainingIgnoreCase(String accion, String detalle);
}


