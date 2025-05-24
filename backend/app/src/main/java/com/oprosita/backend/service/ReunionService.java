package com.oprosita.backend.service;

import com.oprosita.backend.dto.ReunionDto;
import java.util.List;

public interface ReunionService {
    // Métodos CRUD
    ReunionDto obtenerPorId(Long id);
    List<ReunionDto> obtenerTodos();
    ReunionDto crear(ReunionDto dto);
    ReunionDto actualizar(Long id, ReunionDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<ReunionDto> obtenerReunionesPorGrupo(Long grupoId);
    ReunionDto crearReunionParaGrupo(Long grupoId, ReunionDto reunionDto);
}