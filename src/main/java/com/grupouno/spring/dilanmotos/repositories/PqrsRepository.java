package com.grupouno.spring.dilanmotos.repositories;

import com.grupouno.spring.dilanmotos.models.PQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad {@link PQRS}.
 * 
 * <p>Extiende de {@link JpaRepository}, lo que proporciona métodos CRUD
 * (crear, leer, actualizar, eliminar) y soporte para consultas personalizadas.</p>
 * 
 * <p>Este repositorio permite acceder y manipular registros de la tabla <b>pqrs</b>
 * en la base de datos.</p>
 * 
 * <p>Métodos principales:</p>
 * <ul>
 *   <li><b>findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase</b>: 
 *       Busca registros de PQRS cuyo tipo o asunto contenga la cadena indicada,
 *       ignorando mayúsculas y minúsculas.</li>
 * </ul>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 * List<PQRS> resultados = pqrsRepository
 *     .findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase("queja", "servicio");
 * </pre>
 * 
 * @author Neyder Estiben Manrique Alvarez
 * @version 1.0
 */
@Repository
public interface PqrsRepository extends JpaRepository<PQRS, Integer> {

    /**
     * Busca registros de PQRS cuyo tipo o asunto contenga las cadenas especificadas,
     * ignorando mayúsculas y minúsculas.
     *
     * @param tipo   texto a buscar dentro del campo tipo
     * @param asunto texto a buscar dentro del campo asunto
     * @return lista de registros que coinciden con los criterios
     */
    List<PQRS> findByTipoContainingIgnoreCaseOrAsuntoContainingIgnoreCase(String tipo, String asunto);
}
