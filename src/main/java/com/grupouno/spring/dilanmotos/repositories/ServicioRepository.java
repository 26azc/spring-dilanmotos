package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    
    // Filtro para el buscador de admin
    List<Servicio> findByComentarioContainingIgnoreCaseOrEstadoServicioContainingIgnoreCase(String comentario, String estado);

    // 💡 IMPORTANTE: Busca por el idUsuario dentro del objeto Usuarios
    List<Servicio> findByUsuario_IdUsuario(Integer idUsuario);
}