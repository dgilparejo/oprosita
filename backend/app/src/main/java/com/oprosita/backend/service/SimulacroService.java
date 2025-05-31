package com.oprosita.backend.service;

import com.oprosita.backend.dto.SimulacroDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SimulacroService {
    // Métodos CRUD
    SimulacroDto obtenerPorId(Long id);
    List<SimulacroDto> obtenerTodos();
    SimulacroDto crear(SimulacroDto dto);
    SimulacroDto actualizar(Long id, SimulacroDto dto);
    void eliminar(Long id);

    // Métodos del OpenAPI
    SimulacroDto crearSimulacro(String descripcion, MultipartFile file);
}