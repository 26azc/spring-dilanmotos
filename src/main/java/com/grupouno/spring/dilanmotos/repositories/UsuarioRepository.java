package com.grupouno.spring.dilanmotos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupouno.spring.dilanmotos.spring_dilanmotos.models.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
}
