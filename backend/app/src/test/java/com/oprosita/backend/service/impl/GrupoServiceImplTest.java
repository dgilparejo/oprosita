package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.AlumnoDto;
import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.exception.AlreadyExistsException;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.AlumnoMapper;
import com.oprosita.backend.mapper.ContenidoItemMapper;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.model.Alumno;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.repository.AlumnoRepository;
import com.oprosita.backend.repository.ContenidoItemRepository;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.service.MesService;
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
class GrupoServiceImplTest {

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private ContenidoItemRepository contenidoItemRepository;

    @Mock
    private MesService mesService;

    @Mock
    private GrupoMapper grupoMapper;

    @Mock
    private AlumnoMapper alumnoMapper;

    @Mock
    private ContenidoItemMapper contenidoItemMapper;

    @InjectMocks
    private GrupoServiceImpl grupoService;

    @Test
    void givenId_whenGetGrupo_thenReturnGrupo() {
        // given
        Grupo grupo = new Grupo();
        GrupoDto grupoDto = new GrupoDto();

        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));
        given(grupoMapper.toGrupoDto(grupo)).willReturn(grupoDto);

        // when
        GrupoDto result = grupoService.obtenerPorId(1L);

        // then
        assertEquals(grupoDto, result);
        then(grupoRepository).should().findById(1L);
        then(grupoMapper).should().toGrupoDto(grupo);
    }

    @Test
    void givenId_whenGetGrupo_thenThrowException() {
        // given
        given(grupoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> grupoService.obtenerPorId(1L));
        then(grupoRepository).should().findById(1L);
    }

    @Test
    void whenGetAllGrupos_thenReturnGrupoList() {
        // given
        Grupo grupo1 = new Grupo();
        Grupo grupo2 = new Grupo();
        GrupoDto grupoDto1 = new GrupoDto();
        GrupoDto grupoDto2 = new GrupoDto();

        given(grupoRepository.findAll()).willReturn(List.of(grupo1, grupo2));
        given(grupoMapper.toGrupoDto(grupo1)).willReturn(grupoDto1);
        given(grupoMapper.toGrupoDto(grupo2)).willReturn(grupoDto2);

        // when
        List<GrupoDto> result = grupoService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(grupoDto1));
        assertTrue(result.contains(grupoDto2));
        then(grupoRepository).should().findAll();
        then(grupoMapper).should().toGrupoDto(grupo1);
        then(grupoMapper).should().toGrupoDto(grupo2);
    }

    @Test
    void givenGrupoDto_whenCreate_thenReturnSavedGrupo() {
        // given
        GrupoDto inputDto = new GrupoDto();
        inputDto.setNombre("Test");
        Grupo grupo = new Grupo();
        Grupo savedGrupo = new Grupo();
        GrupoDto outputDto = new GrupoDto();

        given(grupoRepository.existsByNombre("Test")).willReturn(false);
        given(grupoMapper.toGrupoEntity(inputDto)).willReturn(grupo);
        given(grupoRepository.save(grupo)).willReturn(savedGrupo);
        given(grupoMapper.toGrupoDto(savedGrupo)).willReturn(outputDto);

        // when
        GrupoDto result = grupoService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
        then(grupoRepository).should().existsByNombre("Test");
        then(grupoMapper).should().toGrupoEntity(inputDto);
        then(grupoRepository).should().save(grupo);
        then(grupoMapper).should().toGrupoDto(savedGrupo);
    }

    @Test
    void givenDtoWithId_whenCreate_thenThrowException() {
        // given
        GrupoDto dto = new GrupoDto();
        dto.setId(1);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> grupoService.crear(dto));
    }

    @Test
    void givenExistingGrupo_whenCreate_thenThrowAlreadyExists() {
        // given
        GrupoDto dto = new GrupoDto();
        dto.setNombre("Duplicado");

        given(grupoRepository.existsByNombre("Duplicado")).willReturn(true);

        // when & then
        assertThrows(AlreadyExistsException.class, () -> grupoService.crear(dto));
        then(grupoRepository).should().existsByNombre("Duplicado");
    }

    @Test
    void givenGrupoId_whenDelete_thenGrupoEliminado() {
        // given
        given(grupoRepository.existsById(1L)).willReturn(true);

        // when
        grupoService.eliminar(1L);

        // then
        then(grupoRepository).should().existsById(1L);
        then(grupoRepository).should().deleteById(1L);
    }

    @Test
    void givenGrupoId_whenDelete_thenThrowNotFound() {
        // given
        given(grupoRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> grupoService.eliminar(1L));
        then(grupoRepository).should().existsById(1L);
    }

    @Test
    void givenGrupoId_whenGetAlumnos_thenReturnList() {
        // given
        Grupo grupo = new Grupo();
        Alumno alumno = new Alumno();
        AlumnoDto alumnoDto = new AlumnoDto();
        grupo.setAlumnos(List.of(alumno));

        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));
        given(alumnoMapper.toAlumnoDto(alumno)).willReturn(alumnoDto);

        // when
        List<AlumnoDto> result = grupoService.obtenerAlumnosPorGrupo(1L);

        // then
        assertEquals(1, result.size());
        assertEquals(alumnoDto, result.get(0));
        then(grupoRepository).should().findById(1L);
        then(alumnoMapper).should().toAlumnoDto(alumno);
    }

    @Test
    void givenGrupoId_whenGetAlumnos_thenThrowNotFound() {
        // given
        given(grupoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> grupoService.obtenerAlumnosPorGrupo(1L));
        then(grupoRepository).should().findById(1L);
    }

    @Test
    void givenGrupoIdAndMesDto_whenAddMes_thenReturnMesDto() {
        // given
        Grupo grupo = new Grupo();
        grupo.setId(1L);
        MesDto mesDto = new MesDto();

        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));
        given(mesService.crear(mesDto)).willReturn(mesDto);

        // when
        MesDto result = grupoService.agregarMesAGrupo(1L, mesDto);

        // then
        assertEquals(mesDto, result);
        then(grupoRepository).should().findById(1L);
        then(mesService).should().crear(mesDto);
    }
}
