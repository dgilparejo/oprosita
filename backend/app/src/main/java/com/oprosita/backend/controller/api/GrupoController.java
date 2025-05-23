package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.GruposApi;
import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.ContenidoItemDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.mapper.GeneralMapper;
import com.oprosita.backend.model.generated.Alumno;
import com.oprosita.backend.model.generated.ContenidoItem;
import com.oprosita.backend.model.generated.Grupo;
import com.oprosita.backend.model.generated.Mes;
import com.oprosita.backend.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<Alumno>> getAlumnosByGrupo(Integer grupoId) {
        List<AlumnoDto> dtos = grupoService.obtenerAlumnosPorGrupo(grupoId.longValue());
        List<Alumno> alumnos = dtos.stream().map(mapper::toAlumnoGenerated).collect(Collectors.toList());
        return ResponseEntity.ok(alumnos);
    }

    @Override
    public ResponseEntity<Void> addAlumnoToGrupo(Integer grupoId, Alumno alumno) {
        AlumnoDto dto = mapper.toAlumnoDto(alumno);
        grupoService.agregarAlumnoAGrupo(grupoId.longValue(), dto);
        return ResponseEntity.status(201).build(); // sin body
    }

    @Override
    public ResponseEntity<Void> removeAlumnoFromGrupo(Integer grupoId, Integer alumnoId) {
        grupoService.eliminarAlumnoDeGrupo(grupoId.longValue(), alumnoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ContenidoItem>> getContenidoByGrupoAndMes(Integer grupoId, String mes) {
        List<ContenidoItemDto> dtos = grupoService.obtenerContenidoPorGrupoYMes(grupoId.longValue(), mes);
        List<ContenidoItem> contenido = dtos.stream().map(mapper::toContenidoItemGenerated).collect(Collectors.toList());
        return ResponseEntity.ok(contenido);
    }

    @Override
    public ResponseEntity<ContenidoItem> addContenidoToGrupoByMes(Integer grupoId, String mes, ContenidoItem contenidoItem) {
        ContenidoItemDto dto = mapper.toContenidoItemDto(contenidoItem);
        ContenidoItemDto creado = grupoService.agregarContenidoAGrupoPorMes(grupoId.longValue(), mes, dto);
        return ResponseEntity.status(201).body(mapper.toContenidoItemGenerated(creado));
    }

    @Override
    public ResponseEntity<Void> addMesToGrupo(Integer grupoId, Mes mes) {
        MesDto dto = mapper.toMesDto(mes);
        grupoService.agregarMesAGrupo(grupoId.longValue(), dto);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Void> createGrupo(Grupo grupo) {
        GrupoDto dto = mapper.toGrupoDto(grupo);
        grupoService.crear(dto);
        return ResponseEntity.status(201).build();
    }

    @Override
    public ResponseEntity<Grupo> getGrupoById(Integer id) {
        GrupoDto dto = grupoService.obtenerPorId(id.longValue());
        return ResponseEntity.ok(mapper.toGrupoGenerated(dto));
    }

    @Override
    public ResponseEntity<List<Grupo>> getGrupos() {
        List<GrupoDto> dtos = grupoService.obtenerTodos();
        List<Grupo> grupos = dtos.stream().map(mapper::toGrupoGenerated).collect(Collectors.toList());
        return ResponseEntity.ok(grupos);
    }

    @Override
    public ResponseEntity<List<Mes>> getMesesByGrupo(Integer grupoId) {
        List<MesDto> dtos = grupoService.obtenerMesesPorGrupo(grupoId.longValue());
        List<Mes> meses = dtos.stream().map(mapper::toMesGenerated).collect(Collectors.toList());
        return ResponseEntity.ok(meses);
    }

    @Override
    public ResponseEntity<Void> deleteContenidoFromGrupoByMes(Integer grupoId, String mes, Integer contenidoId) {
        grupoService.eliminarContenidoDeGrupoPorMes(grupoId.longValue(), mes, contenidoId.longValue());
        return ResponseEntity.noContent().build();
    }
}