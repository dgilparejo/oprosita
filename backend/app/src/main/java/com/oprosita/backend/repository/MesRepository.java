package com.oprosita.backend.repository;

import com.oprosita.backend.model.generated.Mes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesRepository extends JpaRepository<Mes, Long> {
}
