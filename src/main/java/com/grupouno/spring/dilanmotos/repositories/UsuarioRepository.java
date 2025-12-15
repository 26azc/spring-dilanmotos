package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {
    List<Usuarios> findByNombreContainingIgnoreCaseOrCorreoContainingIgnoreCase(String nombre, String correo);

    Optional<Usuarios> findByCorreo(String correo);

 @Query("SELECT u FROM Usuarios u LEFT JOIN FETCH u.motos WHERE u.correo = :correo")
Optional<Usuarios> findByCorreoConMotos(@Param("correo") String correo);
}

