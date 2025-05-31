package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ReunionesApi;
import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.mapper.ReunionMapper;
import com.oprosita.backend.model.generated.Reunion;
import com.oprosita.backend.service.ReunionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReunionController implements ReunionesApi {

    private final ReunionService reunionService;
    private final ReunionMapper mapper;

    @Override
    public ResponseEntity<List<Reunion>> getReunionesByGrupo(Integer grupoId) {
        List<ReunionDto> dtos = reunionService.obtenerReunionesPorGrupo(grupoId.longValue());
        List<Reunion> reuniones = dtos.stream()
                .map(mapper::toGeneratedReunion)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reuniones);
    }

    @Override
    public ResponseEntity<Reunion> createReunion(Integer grupoId, Reunion body) {
        ReunionDto dto = mapper.fromGeneratedReunion(body);
        ReunionDto creado = reunionService.crearReunionParaGrupo(grupoId.longValue(), dto);
        return ResponseEntity.status(201).body(mapper.toGeneratedReunion(creado));
    }

    @Override
    public ResponseEntity<Void> deleteReunion(Integer id) {
        reunionService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Reunion> updateReunion(Integer id, Reunion body) {
        ReunionDto dto = mapper.fromGeneratedReunion(body);
        ReunionDto actualizado = reunionService.actualizar(id.longValue(), dto);
        return ResponseEntity.ok(mapper.toGeneratedReunion(actualizado));
    }
}
