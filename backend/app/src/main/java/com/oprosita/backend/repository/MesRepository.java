package com.oprosita.backend.repository;

import com.oprosita.backend.model.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesRepository extends JpaRepository<Mes, Long> {
    Optional<Mes> findByNombreAndGrupoId(String nombre, Long grupoId);
}
