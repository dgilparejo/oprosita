package com.oprosita.backend.service;

import com.oprosita.backend.dto.MensajeDto;
import com.oprosita.backend.dto.ConversacionDto;
import java.util.List;

public interface ChatService {
    // Métodos CRUD
    MensajeDto obtenerMensajePorId(Long id);
    List<MensajeDto> obtenerMensajes(Long remitente, Long destinatario);
    MensajeDto enviarMensaje(MensajeDto mensajeDto);
    void marcarComoLeido(Long mensajeId);

    // Métodos del OpenAPI
    List<ConversacionDto> obtenerConversacionesPorUsuario(Long usuarioId);
}