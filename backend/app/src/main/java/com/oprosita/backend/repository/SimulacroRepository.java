package com.oprosita.backend.repository;

import com.oprosita.backend.model.generated.Simulacro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacroRepository extends JpaRepository<Simulacro, Long> {
}
