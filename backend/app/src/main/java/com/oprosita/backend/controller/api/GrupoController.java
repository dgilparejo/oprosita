package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.GruposApi;
import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.ContenidoItem;
import com.oprosita.backend.model.Mes;
import com.oprosita.backend.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GrupoController implements GruposApi {

    private final GrupoService grupoService;
    private final GeneralMapper mapper;

    @Override
    public ResponseEntity<Void> deleteGrupo(Integer id) {
        grupoService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<AlumnoDto>> getAlumnosByGrupo(Integer grupoId) {
        return ResponseEntity.ok(grupoService.obtenerAlumnosPorGrupo(grupoId.longValue()));
    }

    @Override
    public ResponseEntity<AlumnoDto> addAlumnoToGrupo(Integer grupoId, com.oprosita.backend.model.generated.Alumno alumno) {
        AlumnoDto alumnoDto = mapper.toAlumnoDto(mapper.toAlumnoEntity(alumno));
        AlumnoDto result = grupoService.agregarAlumnoAGrupo(grupoId.longValue(), alumnoDto);
        return ResponseEntity.status(201).body(result);
    }

    @Override
    public ResponseEntity<Void> removeAlumnoFromGrupo(Integer grupoId, Integer alumnoId) {
        grupoService.eliminarAlumnoDeGrupo(grupoId.longValue(), alumnoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ContenidoItemDto>> getContenidoByGrupoAndMes(Integer grupoId, String mes) {
        List<ContenidoItemDto> contenido = grupoService.obtenerContenidoPorGrupoYMes(grupoId.longValue(), mes);
        return ResponseEntity.ok(contenido);
    }

    @Override
    public ResponseEntity<ContenidoItemDto> addContenidoToGrupoByMes(Integer grupoId, String mes, com.oprosita.backend.model.generated.ContenidoItem contenidoItem) {
        ContenidoItemDto dto = mapper.toContenidoItemDto(mapper.toContenidoItemEntity(contenidoItem));
        ContenidoItemDto creado = grupoService.agregarContenidoAGrupoPorMes(grupoId.longValue(), mes, dto);
        return ResponseEntity.status(201).body(creado);
    }

    @Override
    public ResponseEntity<Void> addMesToGrupo(Integer grupoId, Mes mes) {
        return ResponseEntity.status(501).build(); // NOT_IMPLEMENTED
    }

    @Override
    public ResponseEntity<Void> createGrupo(Grupo grupo) {
        GrupoDto grupoDto = mapper.toGrupoDto(mapper.toGrupoEntity(grupo));
        grupoService.crear(grupoDto);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<com.oprosita.backend.model.generated.Grupo> getGrupoById(Integer id) {
        GrupoDto grupoDto = grupoService.obtenerPorId(id.longValue());
        com.oprosita.backend.model.generated.Grupo grupo = mapper.toGrupoGenerated(grupoDto);
        return ResponseEntity.ok(grupo);
    }

    @Override
    public ResponseEntity<List<GrupoDto>> getGrupos() {
        return ResponseEntity.ok(grupoService.obtenerTodos());
    }

    @Override
    public ResponseEntity<Void> getMesesByGrupo(Integer grupoId) {
        // Similar al addMesToGrupo, este método no está en GrupoService.
        // Puedes ignorarlo o implementarlo si luego añades lógica en el servicio.
        return ResponseEntity.status(501).build();
    }
    @Override
    public ResponseEntity<Void> deleteContenidoFromGrupoByMes(Integer grupoId, String mes, Integer contenidoId) {
        grupoService.eliminarContenidoDeGrupoPorMes(grupoId.longValue(), mes, contenidoId.longValue());
        return ResponseEntity.noContent().build();
    }

}
