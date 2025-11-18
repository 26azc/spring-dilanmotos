package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer>{

        List<TipoServicio> findByNombreContainingIgnoreCase(String nombre); 

}
