package com.oprosita.backend.service;

import com.oprosita.backend.dto.NovedadDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface NovedadService {
    // Métodos CRUD
    NovedadDto obtenerPorId(Long id);
    List<NovedadDto> obtenerTodos();
    NovedadDto crear(NovedadDto dto);
    NovedadDto actualizar(Long id, NovedadDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    List<NovedadDto> obtenerNovedadesProfesor();
    NovedadDto crearNovedadProfesor(NovedadDto novedadDto);
    List<NovedadDto> obtenerNovedadesAlumno();
    NovedadDto crearNovedadAlumno(NovedadDto novedadDto);
    NovedadDto actualizarNovedadAlumno(NovedadDto novedadDto);
    void eliminarNovedadesAlumno();
    List<NovedadDto> obtenerNovedadesProfesorPorTipo(String tipo, OffsetDateTime fechaDesde);
    void registrarNovedadAutomatica(Long alumnoId, String accion, String detalle);
}