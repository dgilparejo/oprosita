package com.oprosita.backend.controller.api;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class GrupoControllerTest {

    @Mock
    private GrupoService grupoService;

    @Mock
    private ProfesorService profesorService;

    @Mock
    private GrupoMapper grupoMapper;

    @Mock
    private AlumnoMapper alumnoMapper;

    @Mock
    private ProfesorMapper profesorMapper;

    @Mock
    private MesMapper mesMapper;

    @InjectMocks
    private GrupoController grupoController;

    @Test
    void givenGrupoId_whenDelete_thenNoContent() {
        // given
        Integer id = 1;
        willDoNothing().given(grupoService).eliminar(id.longValue());

        // when
        ResponseEntity<Void> response = grupoController.deleteGrupo(id);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(grupoService).should().eliminar(id.longValue());
    }

    @Test
    void givenGrupoId_whenGetAlumnos_thenReturnListOfAlumnos() {
        // given
        Integer grupoId = 1;
        AlumnoDto dto1 = new AlumnoDto();
        AlumnoDto dto2 = new AlumnoDto();
        Alumno alumno1 = new Alumno();
        Alumno alumno2 = new Alumno();

        given(grupoService.obtenerAlumnosPorGrupo(grupoId.longValue())).willReturn(List.of(dto1, dto2));
        given(alumnoMapper.toGeneratedAlumno(dto1)).willReturn(alumno1);
        given(alumnoMapper.toGeneratedAlumno(dto2)).willReturn(alumno2);

        // when
        ResponseEntity<List<Alumno>> response = grupoController.getAlumnosByGrupo(grupoId);

        // then
        assertThat(response.getBody()).containsExactly(alumno1, alumno2);
        then(grupoService).should().obtenerAlumnosPorGrupo(grupoId.longValue());
        then(alumnoMapper).should(times(2)).toGeneratedAlumno(any(AlumnoDto.class));
    }

    @Test
    void givenGrupoIdAndAlumno_whenAddAlumno_thenReturnAlumnoCreated() {
        // given
        Integer grupoId = 1;
        AddAlumnoToGrupoRequest request = new AddAlumnoToGrupoRequest();
        AlumnoDto dto = new AlumnoDto();
        AlumnoDto creado = new AlumnoDto();
        Alumno alumno = new Alumno();

        given(alumnoMapper.fromGeneratedAlumno(request)).willReturn(dto);
        given(grupoService.agregarAlumnoAGrupo(grupoId.longValue(), dto)).willReturn(creado);
        given(alumnoMapper.toGeneratedAlumno(creado)).willReturn(alumno);

        // when
        ResponseEntity<Alumno> response = grupoController.addAlumnoToGrupo(grupoId, request);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(alumno);
    }

    @Test
    void givenGrupoIdAndAlumnoId_whenRemoveAlumno_thenNoContent() {
        // given
        Integer grupoId = 1;
        Integer alumnoId = 2;
        willDoNothing().given(grupoService).eliminarAlumnoDeGrupo(grupoId.longValue(), alumnoId.longValue());

        // when
        ResponseEntity<Void> response = grupoController.removeAlumnoFromGrupo(grupoId, alumnoId);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(grupoService).should().eliminarAlumnoDeGrupo(grupoId.longValue(), alumnoId.longValue());
    }

    @Test
    void givenGrupoIdAndMes_whenAddMes_thenReturnMesCreated() {
        // given
        Integer grupoId = 1;
        Mes mes = new Mes();
        MesDto dto = new MesDto();
        MesDto creado = new MesDto();
        Mes mesCreated = new Mes();

        given(mesMapper.fromGeneratedMes(mes)).willReturn(dto);
        given(grupoService.agregarMesAGrupo(grupoId.longValue(), dto)).willReturn(creado);
        given(mesMapper.toGeneratedMes(creado)).willReturn(mesCreated);

        // when
        ResponseEntity<Mes> response = grupoController.addMesToGrupo(grupoId, mes);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(mesCreated);
    }

    @Test
    void givenGrupo_whenCreateGrupo_thenReturnGrupoCreated() {
        // given
        Grupo grupo = new Grupo();
        GrupoDto dto = new GrupoDto();
        GrupoDto creado = new GrupoDto();
        Grupo grupoCreated = new Grupo();

        given(grupoMapper.fromGeneratedGrupo(grupo)).willReturn(dto);
        given(grupoService.crear(dto)).willReturn(creado);
        given(grupoMapper.toGeneratedGrupo(creado)).willReturn(grupoCreated);

        // when
        ResponseEntity<Grupo> response = grupoController.createGrupo(grupo);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(grupoCreated);
    }

    @Test
    void givenGrupoId_whenGetGrupo_thenReturnGrupo() {
        // given
        Integer id = 1;
        GrupoDto dto = new GrupoDto();
        Grupo grupo = new Grupo();

        given(grupoService.obtenerPorId(id.longValue())).willReturn(dto);
        given(grupoMapper.toGeneratedGrupo(dto)).willReturn(grupo);

        // when
        ResponseEntity<Grupo> response = grupoController.getGrupoById(id);

        // then
        assertThat(response.getBody()).isEqualTo(grupo);
    }

    @Test
    void whenGetAllGrupos_thenReturnGrupoList() {
        // given
        GrupoDto dto1 = new GrupoDto();
        GrupoDto dto2 = new GrupoDto();
        Grupo g1 = new Grupo();
        Grupo g2 = new Grupo();

        given(grupoService.obtenerTodos()).willReturn(List.of(dto1, dto2));
        given(grupoMapper.toGeneratedGrupo(dto1)).willReturn(g1);
        given(grupoMapper.toGeneratedGrupo(dto2)).willReturn(g2);

        // when
        ResponseEntity<List<Grupo>> response = grupoController.getGrupos();

        // then
        assertThat(response.getBody()).containsExactly(g1, g2);
    }

    @Test
    void givenGrupoId_whenGetMeses_thenReturnMesList() {
        // given
        Integer grupoId = 1;
        MesDto dto1 = new MesDto();
        MesDto dto2 = new MesDto();
        Mes m1 = new Mes();
        Mes m2 = new Mes();

        given(grupoService.obtenerMesesPorGrupo(grupoId.longValue())).willReturn(List.of(dto1, dto2));
        given(mesMapper.toGeneratedMes(dto1)).willReturn(m1);
        given(mesMapper.toGeneratedMes(dto2)).willReturn(m2);

        // when
        ResponseEntity<List<Mes>> response = grupoController.getMesesByGrupo(grupoId);

        // then
        assertThat(response.getBody()).containsExactly(m1, m2);
    }

    @Test
    void givenGrupoIdAndProfesor_whenAddProfesor_thenReturnProfesorCreated() {
        // given
        Integer grupoId = 1;
        Profesor profesor = new Profesor().id(2);
        ProfesorDto dto = new ProfesorDto();
        dto.setId(99);

        ProfesorDto actualizado = new ProfesorDto();
        Profesor mapped = new Profesor();

        given(profesorMapper.fromGeneratedProfesor(profesor)).willReturn(dto);
        given(profesorService.asignarGrupoAProfesor(99L, grupoId.longValue())).willReturn(actualizado);
        given(profesorMapper.toGeneratedProfesor(actualizado)).willReturn(mapped);

        // when
        ResponseEntity<Profesor> response = grupoController.addProfesorToGrupo(grupoId, profesor);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(mapped);

        then(profesorMapper).should().fromGeneratedProfesor(profesor);
        then(profesorService).should().asignarGrupoAProfesor(99L, 1L);
        then(profesorMapper).should().toGeneratedProfesor(actualizado);
    }

    @Test
    void givenGrupoIdAndProfesorId_whenRemoveProfesor_thenNoContent() {
        // given
        Integer grupoId = 1, profesorId = 2;
        willDoNothing().given(profesorService).desasignarGrupoDeProfesor(profesorId.longValue(), grupoId.longValue());

        // when
        ResponseEntity<Void> response = grupoController.removeProfesorFromGrupo(grupoId, profesorId);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(204);
        then(profesorService).should().desasignarGrupoDeProfesor(profesorId.longValue(), grupoId.longValue());
    }

    @Test
    void givenGrupoId_whenGetProfesores_thenReturnProfesorList() {
        // given
        Integer grupoId = 1;
        ProfesorDto dto = new ProfesorDto();
        Profesor profesor = new Profesor();

        given(profesorService.obtenerProfesorPorGrupo(grupoId.longValue())).willReturn(dto);
        given(profesorMapper.toGeneratedProfesor(dto)).willReturn(profesor);

        // when
        ResponseEntity<List<Profesor>> response = grupoController.getProfesoresByGrupo(grupoId);

        // then
        assertThat(response.getBody()).containsExactly(profesor);
    }
}
