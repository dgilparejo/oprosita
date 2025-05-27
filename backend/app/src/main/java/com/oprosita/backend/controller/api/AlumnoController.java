package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.AlumnosApi;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.service.AlumnoService;
import com.oprosita.backend.service.ContenidoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AlumnoController implements AlumnosApi {

    private final AlumnoService alumnoService;
    private final ContenidoItemService contenidoItemService;
    private final ContenidoItemMapper mapper;
    private final GrupoMapper grupoMapper;

    @Override
    public ResponseEntity<Void> deleteAlumno(Integer id) {
        alumnoService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Grupo>> getGruposByAlumno(Integer alumnoId) {
        List<GrupoDto> gruposDto = alumnoService.obtenerGrupoPorAlumno(alumnoId.longValue());

        List<Grupo> grupos = gruposDto.stream()
                .map(grupoMapper::toGeneratedGrupo) // Asegúrate de tener este método
                .collect(Collectors.toList());

        return ResponseEntity.ok(grupos);
    }

}
