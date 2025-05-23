package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.ProfesoresApi;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.service.ProfesorService;
import com.oprosita.backend.mapper.GeneralMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfesorController implements ProfesoresApi {

    private final ProfesorService profesorService;
    private final GeneralMapper mapper;

    @Override
    public ResponseEntity<List<Grupo>> getGruposByProfesor(Integer profesorId) {
        List<GrupoDto> dtos = profesorService.obtenerGruposPorProfesor(profesorId.longValue());
        List<Grupo> grupos = dtos.stream()
                .map(mapper::toGrupoGenerated)
                .collect(Collectors.toList());
        return ResponseEntity.ok(grupos);
    }
}
