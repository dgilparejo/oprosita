package com.oprosita.backend.repository;

import com.oprosita.backend.model.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {
}
