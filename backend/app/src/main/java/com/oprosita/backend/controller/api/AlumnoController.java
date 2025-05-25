package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.AlumnosApi;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.generated.ContenidoItem;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.service.AlumnoService;
import com.oprosita.backend.service.ContenidoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Void> deleteContenidoFromAlumno(Integer alumnoId, Integer contenidoId) {
        alumnoService.eliminarContenidoDeAlumno(alumnoId.longValue(), contenidoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addContenidoToAlumno(
            Integer alumnoId,
            String texto,
            String tipoContenido,
            String mes,
            MultipartFile file) {
        alumnoService.agregarContenidoAAlumno(alumnoId.longValue(), texto, tipoContenido, mes, file);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<List<ContenidoItem>> getContenidoByAlumno(Integer alumnoId) {
        List<ContenidoItemDto> dtos = contenidoItemService.obtenerPorAlumno(alumnoId.longValue());

        List<ContenidoItem> result = dtos.stream()
                .map(mapper::toGeneratedContenidoItem) // <-- asegúrate de tener este método
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
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
