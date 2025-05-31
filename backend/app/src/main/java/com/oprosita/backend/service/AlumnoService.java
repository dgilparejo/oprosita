package com.oprosita.backend.service;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;

import java.util.List;

public interface AlumnoService {
    // Métodos CRUD
    AlumnoDto obtenerPorId(Long id);
    List<AlumnoDto> obtenerTodos();
    AlumnoDto crear(AlumnoDto dto);
    AlumnoDto actualizar(Long id, AlumnoDto dto);
    void eliminar(Long id);
    List<GrupoDto> obtenerGrupoPorAlumno(Long alumnoId);
}