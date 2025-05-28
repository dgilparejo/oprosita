package com.oprosita.backend.service;

import com.oprosita.backend.dto.MesDto;
import java.util.List;

public interface MesService {
    // Métodos CRUD
    MesDto obtenerPorId(Long id);
    List<MesDto> obtenerTodos();
    MesDto crear(MesDto dto);
    MesDto actualizar(Long id, MesDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<MesDto> obtenerMesesPorGrupo(Long grupoId);
}