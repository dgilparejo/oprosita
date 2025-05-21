package com.oprosita.backend.repository;

import com.oprosita.backend.model.generated.ContenidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidoItemRepository extends JpaRepository<ContenidoItem, Long> {
}
