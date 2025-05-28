package com.oprosita.backend.repository;

import com.oprosita.backend.model.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {
    boolean existsByGrupoId(Long grupoId);
}
