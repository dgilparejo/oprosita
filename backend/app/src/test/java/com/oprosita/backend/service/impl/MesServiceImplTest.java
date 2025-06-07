package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.MesDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.MesMapper;
import com.oprosita.backend.model.Grupo;
import com.oprosita.backend.model.Mes;
import com.oprosita.backend.repository.GrupoRepository;
import com.oprosita.backend.repository.MesRepository;
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
class MesServiceImplTest {

    @Mock
    private MesRepository mesRepository;

    @Mock
    private GrupoRepository grupoRepository;

    @Mock
    private MesMapper mapper;

    @InjectMocks
    private MesServiceImpl mesService;

    @Test
    void givenId_whenObtenerPorId_thenReturnMesDto() {
        // given
        Mes mes = new Mes();
        MesDto mesDto = new MesDto();

        given(mesRepository.findById(1L)).willReturn(Optional.of(mes));
        given(mapper.toMesDto(mes)).willReturn(mesDto);

        // when
        MesDto result = mesService.obtenerPorId(1L);

        // then
        assertEquals(mesDto, result);
        then(mesRepository).should().findById(1L);
        then(mapper).should().toMesDto(mes);
    }

    @Test
    void givenId_whenObtenerPorId_thenThrowNotFound() {
        // given
        given(mesRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> mesService.obtenerPorId(1L));
        then(mesRepository).should().findById(1L);
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void whenObtenerTodos_thenReturnAllMeses() {
        // given
        Mes mes1 = new Mes();
        Mes mes2 = new Mes();
        MesDto dto1 = new MesDto();
        MesDto dto2 = new MesDto();

        given(mesRepository.findAll()).willReturn(List.of(mes1, mes2));
        given(mapper.toMesDto(mes1)).willReturn(dto1);
        given(mapper.toMesDto(mes2)).willReturn(dto2);

        // when
        List<MesDto> result = mesService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        then(mesRepository).should().findAll();
        then(mapper).should().toMesDto(mes1);
        then(mapper).should().toMesDto(mes2);
    }

    @Test
    void givenDto_whenCrear_thenReturnMesDto() {
        // given
        MesDto inputDto = new MesDto();
        Mes mesEntity = new Mes();
        Mes savedMes = new Mes();
        MesDto outputDto = new MesDto();

        given(mapper.toMesEntity(inputDto)).willReturn(mesEntity);
        given(mesRepository.save(mesEntity)).willReturn(savedMes);
        given(mapper.toMesDto(savedMes)).willReturn(outputDto);

        // when
        MesDto result = mesService.crear(inputDto);

        // then
        assertEquals(outputDto, result);
        then(mapper).should().toMesEntity(inputDto);
        then(mesRepository).should().save(mesEntity);
        then(mapper).should().toMesDto(savedMes);
    }

    @Test
    void givenIdAndDto_whenActualizar_thenReturnUpdatedDto() {
        // given
        MesDto inputDto = new MesDto();
        Mes entity = new Mes();
        Mes updated = new Mes();
        MesDto resultDto = new MesDto();

        given(mesRepository.existsById(1L)).willReturn(true);
        given(mapper.toMesEntity(inputDto)).willReturn(entity);
        given(mesRepository.save(entity)).willReturn(updated);
        given(mapper.toMesDto(updated)).willReturn(resultDto);

        // when
        MesDto result = mesService.actualizar(1L, inputDto);

        // then
        assertEquals(resultDto, result);
        then(mesRepository).should().existsById(1L);
        then(mapper).should().toMesEntity(inputDto);
        then(mesRepository).should().save(entity);
        then(mapper).should().toMesDto(updated);
    }

    @Test
    void givenNonExistingId_whenActualizar_thenThrowNotFound() {
        // given
        MesDto inputDto = new MesDto();
        given(mesRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> mesService.actualizar(1L, inputDto));
        then(mesRepository).should().existsById(1L);
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    void givenId_whenEliminar_thenMesEliminado() {
        // given
        given(mesRepository.existsById(1L)).willReturn(true);

        // when
        mesService.eliminar(1L);

        // then
        then(mesRepository).should().existsById(1L);
        then(mesRepository).should().deleteById(1L);
    }

    @Test
    void givenId_whenEliminar_thenThrowNotFound() {
        // given
        given(mesRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> mesService.eliminar(1L));
        then(mesRepository).should().existsById(1L);
        then(mesRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void givenGrupoId_whenObtenerMesesPorGrupo_thenReturnList() {
        // given
        Grupo grupo = new Grupo();
        Mes mes1 = new Mes();
        Mes mes2 = new Mes();
        grupo.setMeses(List.of(mes1, mes2));
        MesDto dto1 = new MesDto();
        MesDto dto2 = new MesDto();

        given(grupoRepository.findById(1L)).willReturn(Optional.of(grupo));
        given(mapper.toMesDto(mes1)).willReturn(dto1);
        given(mapper.toMesDto(mes2)).willReturn(dto2);

        // when
        List<MesDto> result = mesService.obtenerMesesPorGrupo(1L);

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        then(grupoRepository).should().findById(1L);
        then(mapper).should().toMesDto(mes1);
        then(mapper).should().toMesDto(mes2);
    }

    @Test
    void givenGrupoId_whenObtenerMesesPorGrupo_thenThrowNotFound() {
        // given
        given(grupoRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> mesService.obtenerMesesPorGrupo(1L));
        then(grupoRepository).should().findById(1L);
    }
}
