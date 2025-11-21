package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    List<Usuarios> findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(String nombre, String correo);
}

