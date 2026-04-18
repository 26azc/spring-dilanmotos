package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Integer> {

    @Query("SELECT m FROM Moto m JOIN FETCH m.marca JOIN FETCH m.usuario WHERE m.usuario.idUsuario = :idUsuario")
    Optional<Moto> findMotoCompletaPorUsuario(@Param("idUsuario") Integer idUsuario);
    List<Moto> findByUsuario_IdUsuario(Integer idUsuario);
    List<Moto> findByMarca_IdMarca(int idMarca);
}