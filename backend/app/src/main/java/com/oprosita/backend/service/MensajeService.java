package com.oprosita.backend.service;

import com.oprosita.backend.dto.MensajeDto;
import java.util.List;

public interface MensajeService {
    // Métodos CRUD
    MensajeDto obtenerPorId(Long id);
    List<MensajeDto> obtenerPorConversacion(Long remitente, Long destinatario);
    MensajeDto enviar(MensajeDto mensajeDto);
    void marcarComoLeido(Long mensajeId);
}