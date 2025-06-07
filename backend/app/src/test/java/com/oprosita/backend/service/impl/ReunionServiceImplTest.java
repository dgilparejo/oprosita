package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ReunionDto;
import com.oprosita.backend.exception.ConflictException;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.ReunionMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Reunion;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.ReunionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReunionServiceImplTest {

    @Mock
    private ReunionRepository reunionRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private ReunionMapper reunionMapper;

    @InjectMocks
    private ReunionServiceImpl reunionService;

    @Test
    void givenId_whenObtenerPorId_thenReturnDto() {
        // given
        Reunion reunion = new Reunion();
        ReunionDto dto = new ReunionDto();
        given(reunionRepository.findById(1L)).willReturn(Optional.of(reunion));
        given(reunionMapper.toReunionDto(reunion)).willReturn(dto);

        // when
        ReunionDto result = reunionService.obtenerPorId(1L);

        // then
        assertEquals(dto, result);
        then(reunionRepository).should().findById(1L);
        then(reunionMapper).should().toReunionDto(reunion);
    }

    @Test
    void givenId_whenObtenerPorId_thenThrowNotFound() {
        // given
        given(reunionRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> reunionService.obtenerPorId(1L));
    }

    @Test
    void whenObtenerTodos_thenReturnAllDtos() {
        // given
        Reunion r1 = new Reunion();
        Reunion r2 = new Reunion();
        ReunionDto d1 = new ReunionDto();
        ReunionDto d2 = new ReunionDto();

        given(reunionRepository.findAll()).willReturn(List.of(r1, r2));
        given(reunionMapper.toReunionDto(r1)).willReturn(d1);
        given(reunionMapper.toReunionDto(r2)).willReturn(d2);

        // when
        List<ReunionDto> result = reunionService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(d1, d2)));
    }

    @Test
    void givenDto_whenCrear_thenSaveAndReturnDto() {
        // given
        ReunionDto dto = new ReunionDto();
        dto.setFechaHora(OffsetDateTime.now());
        Reunion entity = new Reunion();
        Reunion saved = new Reunion();
        ReunionDto resultDto = new ReunionDto();

        given(reunionMapper.toReunionEntity(dto)).willReturn(entity);
        given(reunionRepository.save(entity)).willReturn(saved);
        given(reunionMapper.toReunionDto(saved)).willReturn(resultDto);

        // when
        ReunionDto result = reunionService.crear(dto);

        // then
        assertEquals(resultDto, result);
    }

    @Test
    void givenIdAndDto_whenActualizar_thenReturnUpdated() {
        // given
        Long id = 1L;
        ReunionDto dto = new ReunionDto();
        dto.setFechaHora(OffsetDateTime.now());
        Reunion entity = new Reunion();
        entity.setId(id);
        Reunion saved = new Reunion();
        ReunionDto expected = new ReunionDto();

        given(reunionRepository.existsById(id)).willReturn(true);
        given(reunionMapper.toReunionEntity(dto)).willReturn(entity);
        given(reunionRepository.save(entity)).willReturn(saved);
        given(reunionMapper.toReunionDto(saved)).willReturn(expected);

        // when
        ReunionDto result = reunionService.actualizar(id, dto);

        // then
        assertEquals(expected, result);
    }

    @Test
    void givenId_whenActualizar_thenThrowNotFound() {
        // given
        given(reunionRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> reunionService.actualizar(1L, new ReunionDto()));
    }

    @Test
    void givenId_whenEliminar_thenDeleteReunion() {
        // given
        Reunion reunion = new Reunion();
        Grupo grupo = new Grupo();
        reunion.setGrupo(grupo);
        given(reunionRepository.findById(1L)).willReturn(Optional.of(reunion));

        // when
        reunionService.eliminar(1L);

        // then
        assertNull(grupo.getReunion());
        then(reunionRepository).should().delete(reunion);
    }

    @Test
    void givenId_whenEliminar_thenThrowNotFound() {
        // given
        given(reunionRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> reunionService.eliminar(1L));
    }

    @Test
    void givenGrupoId_whenObtenerReunionesPorGrupo_thenReturnFilteredDtos() {
        // given
        Grupo grupo = new Grupo();
        grupo.setId(5L);
        Reunion r = new Reunion();
        r.setGrupo(grupo);
        ReunionDto dto = new ReunionDto();

        given(reunionRepository.findAll()).willReturn(List.of(r));
        given(reunionMapper.toReunionDto(r)).willReturn(dto);

        // when
        List<ReunionDto> result = reunionService.obtenerReunionesPorGrupo(5L);

        // then
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void givenGrupoId_whenCrearReunionParaGrupo_thenSaveAndReturn() {
        // given
        Long grupoId = 1L;
        ReunionDto dto = new ReunionDto();
        dto.setFechaHora(OffsetDateTime.now());
        Reunion entity = new Reunion();
        Grupo grupo = new Grupo();
        Reunion saved = new Reunion();
        ReunionDto expected = new ReunionDto();

        given(reunionRepository.existsByGrupoId(grupoId)).willReturn(false);
        given(reunionMapper.toReunionEntity(dto)).willReturn(entity);
        given(grupoRepository.findById(grupoId)).willReturn(Optional.of(grupo));
        given(reunionRepository.save(entity)).willReturn(saved);
        given(reunionMapper.toReunionDto(saved)).willReturn(expected);

        // when
        ReunionDto result = reunionService.crearReunionParaGrupo(grupoId, dto);

        // then
        assertEquals(expected, result);
        assertEquals(grupo, entity.getGrupo());
    }

    @Test
    void givenGrupoWithReunion_whenCrearReunionParaGrupo_thenThrowConflict() {
        // given
        given(reunionRepository.existsByGrupoId(1L)).willReturn(true);

        // when & then
        assertThrows(ConflictException.class, () -> reunionService.crearReunionParaGrupo(1L, new ReunionDto()));
    }

    @Test
    void givenNonExistingGrupo_whenCrearReunionParaGrupo_thenThrowNotFound() {
        // given
        given(reunionRepository.existsByGrupoId(1L)).willReturn(false);
        given(grupoRepository.findById(1L)).willReturn(Optional.empty());
        ReunionDto dto = new ReunionDto();
        dto.setFechaHora(OffsetDateTime.now());
        given(reunionMapper.toReunionEntity(dto)).willReturn(new Reunion());

        // when & then
        assertThrows(NotFoundException.class, () -> reunionService.crearReunionParaGrupo(1L, dto));
    }
}
