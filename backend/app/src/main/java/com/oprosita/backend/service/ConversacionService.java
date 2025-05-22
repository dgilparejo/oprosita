package com.oprosita.backend.service;

import com.oprosita.backend.dto.ConversacionDto;
import java.util.List;

public interface ConversacionService {
    // Método del OpenAPI
    List<ConversacionDto> obtenerPorUsuario(Long usuarioId);
}