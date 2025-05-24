package com.oprosita.backend.repository;

import com.oprosita.backend.model.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {
}
