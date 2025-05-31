package com.oprosita.backend.repository;

import com.oprosita.backend.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByRemitenteAndDestinatario(Long remitente, Long destinatario);

    @Query("SELECT m FROM Mensaje m WHERE m.remitente = :usuarioId OR m.destinatario = :usuarioId ORDER BY m.fechaEnvio DESC")
    List<Mensaje> findConversacionesByUsuarioId(@Param("usuarioId") Long usuarioId);
}
