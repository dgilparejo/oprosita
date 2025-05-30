package com.oprosita.backend.service;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.MesDto;

import java.util.List;

public interface GrupoService {
    // Métodos CRUD
    GrupoDto obtenerPorId(Long id);
    List<GrupoDto> obtenerTodos();
    GrupoDto crear(GrupoDto dto);
    GrupoDto actualizar(Long id, GrupoDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<AlumnoDto> obtenerAlumnosPorGrupo(Long grupoId);
    AlumnoDto agregarAlumnoAGrupo(Long grupoId, AlumnoDto alumnoDto);
    void eliminarAlumnoDeGrupo(Long grupoId, Long alumnoId);
    MesDto agregarMesAGrupo(Long grupoId, MesDto mesDto);
    List<MesDto> obtenerMesesPorGrupo(Long grupoId);
}