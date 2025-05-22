package com.oprosita.backend.service;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlumnoService {
    // Métodos CRUD
    AlumnoDto obtenerPorId(Long id);
    List<AlumnoDto> obtenerTodos();
    AlumnoDto crear(AlumnoDto dto);
    AlumnoDto actualizar(Long id, AlumnoDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<AlumnoDto> obtenerAlumnosPorGrupo(Long grupoId);
    AlumnoDto agregarAlumnoAGrupo(Long grupoId, AlumnoDto alumnoDto);
    void eliminarAlumnoDeGrupo(Long grupoId, Long alumnoId);
    List<ContenidoItemDto> obtenerContenidoPorAlumno(Long alumnoId);
    ContenidoItemDto agregarContenidoAAlumno(Long alumnoId, String texto, String tipoContenido, String mes, MultipartFile file);
    void eliminarContenidoDeAlumno(Long alumnoId, Long contenidoId);
    List<GrupoDto> obtenerGrupoPorAlumno(Long alumnoId);
}