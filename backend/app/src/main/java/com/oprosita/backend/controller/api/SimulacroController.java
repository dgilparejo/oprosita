package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.SimulacrosApi;
import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.model.generated.Simulacro;
import com.oprosita.backend.service.SimulacroService;
import com.oprosita.backend.mapper.GeneralMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SimulacroController implements SimulacrosApi {

    private final SimulacroService simulacroService;
    private final GeneralMapper mapper;

    @Override
    public ResponseEntity<List<Simulacro>> getSimulacros() {
        List<SimulacroDto> dtos = simulacroService.obtenerTodos();
        List<Simulacro> simulacros = dtos.stream()
                .map(mapper::toSimulacroGenerated)
                .collect(Collectors.toList());
        return ResponseEntity.ok(simulacros);
    }

    @Override
    public ResponseEntity<Simulacro> createSimulacro(String descripcion, Integer grupoId, MultipartFile file) {
        SimulacroDto dto = simulacroService.crearSimulacro(descripcion, grupoId != null ? grupoId.longValue() : null, file);
        return ResponseEntity.status(201).body(mapper.toSimulacroGenerated(dto));
    }

    @Override
    public ResponseEntity<Void> deleteSimulacro(Integer id) {
        simulacroService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }
}
