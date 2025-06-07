package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private AlumnoMapper alumnoMapper;

    @Mock
    private GrupoMapper grupoMapper;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Test
    void givenId_whenGetAlumno_thenReturnAlumno() {
        // given
        Alumno alumno = new Alumno();
        AlumnoDto alumnoDto = AlumnoDto.builder().build();

        given(alumnoRepository.findById(1L)).willReturn(Optional.of(alumno));
        given(alumnoMapper.toAlumnoDto(alumno)).willReturn(alumnoDto);

        // when
        AlumnoDto result = alumnoService.obtenerPorId(1L);

        // then
        assertNotNull(result);
        assertEquals(alumnoDto, result);
        then(alumnoRepository).should().findById(1L);
        then(alumnoMapper).should().toAlumnoDto(alumno);
    }

    @Test
    void givenId_whenGetAlumno_thenThrowException() {
        // given
        given(alumnoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> alumnoService.obtenerPorId(1L));
        then(alumnoRepository).should().findById(1L);
    }

    @Test
    void whenGetAllAlumnos_thenReturnAllAlumnos() {
        // given
        Alumno alumno1 = new Alumno();
        Alumno alumno2 = new Alumno();
        AlumnoDto alumnoDto1 = new AlumnoDto();
        AlumnoDto alumnoDto2 = new AlumnoDto();

        given(alumnoRepository.findAll()).willReturn(List.of(alumno1, alumno2));
        given(alumnoMapper.toAlumnoDto(alumno1)).willReturn(alumnoDto1);
        given(alumnoMapper.toAlumnoDto(alumno2)).willReturn(alumnoDto2);

        // when
        List<AlumnoDto> result = alumnoService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(alumnoDto1));
        assertTrue(result.contains(alumnoDto2));
        then(alumnoRepository).should().findAll();
        then(alumnoMapper).should().toAlumnoDto(alumno1);
        then(alumnoMapper).should().toAlumnoDto(alumno2);
    }

    @Test
    void givenAlumno_whenSaveAlumno_thenReturnAlumno() {
        // given
        AlumnoDto inputDto = new AlumnoDto();
        Alumno alumno = new Alumno();
        Alumno savedAlumno = new Alumno();
        AlumnoDto outputDto = new AlumnoDto();

        given(alumnoMapper.toAlumnoEntity(inputDto)).willReturn(alumno);
        given(alumnoRepository.save(alumno)).willReturn(savedAlumno);
        given(alumnoMapper.toAlumnoDto(savedAlumno)).willReturn(outputDto);

        // when
        AlumnoDto result = alumnoService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
        then(alumnoMapper).should().toAlumnoEntity(inputDto);
        then(alumnoRepository).should().save(alumno);
        then(alumnoMapper).should().toAlumnoDto(savedAlumno);
    }

    @Test
    void givenExistingAlumnoIdAndDto_whenUpdate_thenReturnsUpdatedAlumnoDto() {
        // given
        Long alumnoId = 1L;
        AlumnoDto inputDto = new AlumnoDto();
        Alumno alumnoEntity = new Alumno();
        alumnoEntity.setId(alumnoId);
        Alumno savedAlumno = new Alumno();
        AlumnoDto outputDto = new AlumnoDto();

        given(alumnoRepository.existsById(alumnoId)).willReturn(true);
        given(alumnoMapper.toAlumnoEntity(inputDto)).willReturn(alumnoEntity);
        given(alumnoRepository.save(alumnoEntity)).willReturn(savedAlumno);
        given(alumnoMapper.toAlumnoDto(savedAlumno)).willReturn(outputDto);

        // when
        AlumnoDto result = alumnoService.actualizar(alumnoId, inputDto);

        // then
        assertEquals(outputDto, result);
        then(alumnoRepository).should().existsById(alumnoId);
        then(alumnoMapper).should().toAlumnoEntity(inputDto);
        then(alumnoRepository).should().save(alumnoEntity);
        then(alumnoMapper).should().toAlumnoDto(savedAlumno);
    }

    @Test
    void givenNonExistingAlumnoId_whenUpdate_thenThrowsNotFoundException() {
        // given
        Long alumnoId = 1L;
        AlumnoDto inputDto = new AlumnoDto();

        given(alumnoRepository.existsById(alumnoId)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> alumnoService.actualizar(alumnoId, inputDto));
        then(alumnoRepository).should().existsById(alumnoId);
        then(alumnoRepository).shouldHaveNoMoreInteractions();
        then(alumnoMapper).shouldHaveNoInteractions();
    }

    @Test
    void givenIdAlumno_whenDelete_thenAlumnoDeleted() {
        // given
        given(alumnoRepository.existsById(1L)).willReturn(true);

        // when
        alumnoService.eliminar(1L);

        // then
        then(alumnoRepository).should().existsById(1L);
        then(alumnoRepository).should().deleteById(1L);
    }

    @Test
    void givenIdAlumno_whenDelete_thenThrowException() {
        // given
        given(alumnoRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> alumnoService.eliminar(1L));
        then(alumnoRepository).should().existsById(1L);
        then(alumnoRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenAlumnoWithGroup_whenGetGroupByAlumno_thenReturnsGrupoDto() {
        // given
        Grupo grupo = new Grupo();
        Alumno alumno = new Alumno();
        alumno.setGrupo(grupo);
        GrupoDto grupoDto = new GrupoDto();

        given(alumnoRepository.findById(1L)).willReturn(Optional.of(alumno));
        given(grupoMapper.toGrupoDto(grupo)).willReturn(grupoDto);

        // when
        List<GrupoDto> result = alumnoService.obtenerGrupoPorAlumno(1L);

        // then
        assertEquals(1, result.size());
        assertEquals(grupoDto, result.get(0));
        then(alumnoRepository).should().findById(1L);
        then(grupoMapper).should().toGrupoDto(grupo);
    }

    @Test
    void givenAlumnoWithoutGroup_whenGetGroupByAlumno_thenReturnsEmptyList() {
        // given
        Alumno alumno = new Alumno();
        alumno.setGrupo(null);

        given(alumnoRepository.findById(1L)).willReturn(Optional.of(alumno));

        // when
        List<GrupoDto> result = alumnoService.obtenerGrupoPorAlumno(1L);

        // then
        assertTrue(result.isEmpty());
        then(alumnoRepository).should().findById(1L);
        then(grupoMapper).shouldHaveNoInteractions();
    }
}
