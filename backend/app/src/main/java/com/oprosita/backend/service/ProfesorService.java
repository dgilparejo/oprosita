package com.oprosita.backend.service;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.ProfesorDto;
import java.util.List;

public interface ProfesorService {
    // Métodos CRUD
    ProfesorDto obtenerPorId(Long id);
    List<ProfesorDto> obtenerTodos();
    ProfesorDto crear(ProfesorDto dto);
    ProfesorDto actualizar(Long id, ProfesorDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<GrupoDto> obtenerGruposPorProfesor(Long profesorId);
    ProfesorDto asignarGrupoAProfesor(Long profesorId, Long grupoId);
    void desasignarGrupoDeProfesor(Long profesorId, Long grupoId);
}