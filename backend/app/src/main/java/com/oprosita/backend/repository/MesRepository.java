package com.oprosita.backend.repository;

import com.oprosita.backend.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesRepository extends JpaRepository<Mes, Long> {
    @Query("SELECT m FROM Mes m WHERE LOWER(m.nombre) = LOWER(:nombre) AND m.grupo.id = :grupoId")
    Optional<Mes> findByNombreIgnoreCaseAndGrupoIdCustom(@Param("nombre") String nombre, @Param("grupoId") Long grupoId);
}
