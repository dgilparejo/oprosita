package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.GrupoDto;
import com.oprosita.backend.dto.ProfesorDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.GrupoMapper;
import com.oprosita.backend.mapper.ProfesorMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Profesor;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.ProfesorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesorServiceImplTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private ProfesorMapper profesorMapper;

    @Mock
    private GrupoMapper grupoMapper;

    @InjectMocks
    private ProfesorServiceImpl profesorService;

    @Test
    void givenId_whenObtenerProfesor_thenReturnDto() {
        // given
        Profesor profesor = new Profesor();
        ProfesorDto dto = new ProfesorDto();
        given(profesorRepository.findById(1L)).willReturn(Optional.of(profesor));
        given(profesorMapper.toProfesorDto(profesor)).willReturn(dto);

        // when
        ProfesorDto result = profesorService.obtenerPorId(1L);

        // then
        assertEquals(dto, result);
        then(profesorRepository).should().findById(1L);
        then(profesorMapper).should().toProfesorDto(profesor);
    }

    @Test
    void givenId_whenObtenerProfesor_thenThrowNotFound() {
        // given
        given(profesorRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> profesorService.obtenerPorId(1L));
        then(profesorRepository).should().findById(1L);
    }

    @Test
    void whenObtenerTodos_thenReturnAllProfesores() {
        // given
        List<Profesor> profesores = List.of(new Profesor(), new Profesor());
        List<ProfesorDto> dtos = List.of(new ProfesorDto(), new ProfesorDto());
        given(profesorRepository.findAll()).willReturn(profesores);
        given(profesorMapper.toProfesorDtoList(profesores)).willReturn(dtos);

        // when
        List<ProfesorDto> result = profesorService.obtenerTodos();

        // then
        assertEquals(dtos, result);
        then(profesorRepository).should().findAll();
        then(profesorMapper).should().toProfesorDtoList(profesores);
    }

    @Test
    void givenDto_whenCrear_thenReturnSavedDto() {
        // given
        ProfesorDto inputDto = new ProfesorDto();
        Profesor entity = new Profesor();
        Profesor saved = new Profesor();
        ProfesorDto outputDto = new ProfesorDto();

        given(profesorMapper.toProfesorEntity(inputDto)).willReturn(entity);
        given(profesorRepository.save(entity)).willReturn(saved);
        given(profesorMapper.toProfesorDto(saved)).willReturn(outputDto);

        // when
        ProfesorDto result = profesorService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
    }

    @Test
    void givenIdAndDto_whenActualizar_thenReturnUpdatedDto() {
        // given
        Long id = 1L;
        ProfesorDto dto = new ProfesorDto();
        Profesor entity = new Profesor();
        entity.setId(id);
        Profesor saved = new Profesor();
        ProfesorDto output = new ProfesorDto();

        given(profesorRepository.existsById(id)).willReturn(true);
        given(profesorMapper.toProfesorEntity(dto)).willReturn(entity);
        given(profesorRepository.save(entity)).willReturn(saved);
        given(profesorMapper.toProfesorDto(saved)).willReturn(output);

        // when
        ProfesorDto result = profesorService.actualizar(id, dto);

        // then
        assertEquals(output, result);
    }

    @Test
    void givenId_whenEliminar_thenDeleteProfesor() {
        // given
        given(profesorRepository.existsById(1L)).willReturn(true);

        // when
        profesorService.eliminar(1L);

        // then
        then(profesorRepository).should().existsById(1L);
        then(profesorRepository).should().deleteById(1L);
    }

    @Test
    void givenId_whenEliminar_thenThrowNotFound() {
        // given
        given(profesorRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> profesorService.eliminar(1L));
        then(profesorRepository).should().existsById(1L);
        then(profesorRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenProfesorWithGrupos_whenObtenerGrupos_thenReturnDtos() {
        // given
        Profesor profesor = new Profesor();
        Grupo grupo = new Grupo();
        profesor.setGrupos(List.of(grupo));
        List<GrupoDto> grupoDtos = List.of(new GrupoDto());
        given(profesorRepository.findById(1L)).willReturn(Optional.of(profesor));
        given(grupoMapper.toGrupoDtoList(profesor.getGrupos())).willReturn(grupoDtos);

        // when
        List<GrupoDto> result = profesorService.obtenerGruposPorProfesor(1L);

        // then
        assertEquals(grupoDtos, result);
    }

    @Test
    void givenProfesorAndGrupo_whenAsignarGrupo_thenReturnsUpdatedDto() {
        // given
        Profesor profesor = new Profesor();
        profesor.setGrupos(new ArrayList<>());
        Grupo grupo = new Grupo();
        grupo.setId(2L);
        Profesor saved = new Profesor();
        ProfesorDto dto = new ProfesorDto();

        given(profesorRepository.findById(1L)).willReturn(Optional.of(profesor));
        given(grupoRepository.findById(2L)).willReturn(Optional.of(grupo));
        given(grupoRepository.save(grupo)).willReturn(grupo);
        given(profesorRepository.save(profesor)).willReturn(saved);
        given(profesorMapper.toProfesorDto(saved)).willReturn(dto);

        // when
        ProfesorDto result = profesorService.asignarGrupoAProfesor(1L, 2L);

        // then
        assertEquals(dto, result);
    }

    @Test
    void givenGrupoAsignado_whenAsignarGrupo_thenThrowException() {
        // given
        Profesor profesor = new Profesor();
        Grupo grupo = new Grupo();
        grupo.setId(2L);
        profesor.setGrupos(new ArrayList<>(List.of(grupo)));

        given(profesorRepository.findById(1L)).willReturn(Optional.of(profesor));
        given(grupoRepository.findById(2L)).willReturn(Optional.of(grupo));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> profesorService.asignarGrupoAProfesor(1L, 2L));
    }

    @Test
    void givenGrupoAndProfesor_whenDesasignarGrupo_thenRemoveRelationship() {
        // given
        Grupo grupo = new Grupo();
        grupo.setId(2L);
        Profesor profesor = new Profesor();
        profesor.setGrupos(new ArrayList<>(List.of(grupo)));

        given(profesorRepository.findById(1L)).willReturn(Optional.of(profesor));
        given(grupoRepository.findById(2L)).willReturn(Optional.of(grupo));

        // when
        profesorService.desasignarGrupoDeProfesor(1L, 2L);

        // then
        then(grupoRepository).should().save(grupo);
        then(profesorRepository).should().save(profesor);
        assertNull(grupo.getProfesor());
        assertFalse(profesor.getGrupos().contains(grupo));
    }

    @Test
    void givenGrupoConProfesor_whenObtenerProfesorPorGrupo_thenReturnDto() {
        // given
        Profesor profesor = new Profesor();
        Grupo grupo = new Grupo();
        grupo.setProfesor(profesor);
        ProfesorDto dto = new ProfesorDto();

        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));
        given(profesorMapper.toProfesorDto(profesor)).willReturn(dto);

        // when
        ProfesorDto result = profesorService.obtenerProfesorPorGrupo(1L);

        // then
        assertEquals(dto, result);
    }

    @Test
    void givenGrupoSinProfesor_whenObtenerProfesorPorGrupo_thenThrowNotFound() {
        // given
        Grupo grupo = new Grupo();
        grupo.setProfesor(null);
        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));

        // when & then
        assertThrows(NotFoundException.class, () -> profesorService.obtenerProfesorPorGrupo(1L));
    }
}
