package com.oprosita.backend.repository;

import com.oprosita.backend.model.Novedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovedadRepository extends JpaRepository<Novedad, Long> {
}
