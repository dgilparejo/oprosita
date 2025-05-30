package com.oprosita.backend.controller.api;

import com.oprosita.backend.api.GruposApi;
import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.mapper.MesMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.model.generated.*;
import com.oprosita.backend.service.GrupoService;
import com.oprosita.backend.service.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GrupoController implements GruposApi {

    private final GrupoService grupoService;
    private final ProfesorService profesorService;
    private final GrupoMapper grupoMapper;
    private final AlumnoMapper alumnoMapper;
    private final ProfesorMapper profesorMapper;
    private final MesMapper mesMapper;

    @Override
    public ResponseEntity<Void> deleteGrupo(Integer id) {
        grupoService.eliminar(id.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Alumno>> getAlumnosByGrupo(Integer grupoId) {
        List<AlumnoDto> dtos = grupoService.obtenerAlumnosPorGrupo(grupoId.longValue());
        List<Alumno> alumnos = dtos.stream()
                .map(alumnoMapper::toGeneratedAlumno)
                .toList();
        return ResponseEntity.ok(alumnos);
    }

    @Override
    public ResponseEntity<Alumno> addAlumnoToGrupo(Integer grupoId, AddAlumnoToGrupoRequest addAlumnoToGrupoRequest) {
        AlumnoDto dto = alumnoMapper.fromGeneratedAlumno(addAlumnoToGrupoRequest);
        AlumnoDto creado = grupoService.agregarAlumnoAGrupo(grupoId.longValue(), dto);
        return ResponseEntity.status(201).body(alumnoMapper.toGeneratedAlumno(creado));
    }

    @Override
    public ResponseEntity<Void> removeAlumnoFromGrupo(Integer grupoId, Integer alumnoId) {
        grupoService.eliminarAlumnoDeGrupo(grupoId.longValue(), alumnoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Mes> addMesToGrupo(Integer grupoId, Mes mes) {
        MesDto dto = mesMapper.fromGeneratedMes(mes);
        MesDto creado = grupoService.agregarMesAGrupo(grupoId.longValue(), dto);
        return ResponseEntity.status(201).body(mesMapper.toGeneratedMes(creado));
    }

    @Override
    public ResponseEntity<Grupo> createGrupo(Grupo grupo) {
        GrupoDto dto = grupoMapper.fromGeneratedGrupo(grupo);
        GrupoDto creado = grupoService.crear(dto);
        return ResponseEntity.status(201).body(grupoMapper.toGeneratedGrupo(creado));
    }

    @Override
    public ResponseEntity<Grupo> getGrupoById(Integer id) {
        GrupoDto dto = grupoService.obtenerPorId(id.longValue());
        return ResponseEntity.ok(grupoMapper.toGeneratedGrupo(dto));
    }

    @Override
    public ResponseEntity<List<Grupo>> getGrupos() {
        List<GrupoDto> dtos = grupoService.obtenerTodos();
        List<Grupo> grupos = dtos.stream()
                .map(grupoMapper::toGeneratedGrupo)
                .toList();
        return ResponseEntity.ok(grupos);
    }

    @Override
    public ResponseEntity<List<Mes>> getMesesByGrupo(Integer grupoId) {
        List<MesDto> dtos = grupoService.obtenerMesesPorGrupo(grupoId.longValue());
        List<Mes> meses = dtos.stream()
                .map(mesMapper::toGeneratedMes)
                .toList();
        return ResponseEntity.ok(meses);
    }

    @Override
    public ResponseEntity<Profesor> addProfesorToGrupo(Integer grupoId, Profesor profesor) {
        ProfesorDto dto = profesorMapper.fromGeneratedProfesor(profesor);
        // Se llama al método del ProfesorService para asignar grupo
        ProfesorDto actualizado = profesorService.asignarGrupoAProfesor(dto.getId().longValue(), grupoId.longValue());
        return ResponseEntity.status(201).body(profesorMapper.toGeneratedProfesor(actualizado));
    }

    @Override
    public ResponseEntity<Void> removeProfesorFromGrupo(Integer grupoId, Integer profesorId) {
        profesorService.desasignarGrupoDeProfesor(profesorId.longValue(), grupoId.longValue());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Profesor>> getProfesoresByGrupo(Integer grupoId) {
        ProfesorDto dto = profesorService.obtenerProfesorPorGrupo(grupoId.longValue());
        Profesor profesor = profesorMapper.toGeneratedProfesor(dto);
        return ResponseEntity.ok(List.of(profesor)); // lo envolvemos en una lista para cumplir OpenAPI
    }
}
