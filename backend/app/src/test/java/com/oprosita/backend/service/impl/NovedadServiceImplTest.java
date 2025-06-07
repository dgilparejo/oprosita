package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.NovedadDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.NovedadMapper;
import com.oprosita.backend.model.Novedad;
import com.oprosita.backend.model.TipoDestinatario;
import com.oprosita.backend.repository.NovedadRepository;
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
class NovedadServiceImplTest {

    @Mock
    private NovedadRepository novedadRepository;

    @Mock
    private NovedadMapper mapper;

    @InjectMocks
    private NovedadServiceImpl service;

    @Test
    void givenId_whenObtenerPorId_thenReturnDto() {
        // given
        Novedad novedad = new Novedad();
        NovedadDto dto = new NovedadDto();
        given(novedadRepository.findById(1L)).willReturn(Optional.of(novedad));
        given(mapper.toNovedadDto(novedad)).willReturn(dto);

        // when
        NovedadDto result = service.obtenerPorId(1L);

        // then
        assertEquals(dto, result);
        then(novedadRepository).should().findById(1L);
        then(mapper).should().toNovedadDto(novedad);
    }

    @Test
    void givenId_whenObtenerPorId_thenThrowNotFound() {
        // given
        given(novedadRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> service.obtenerPorId(1L));
        then(novedadRepository).should().findById(1L);
    }

    @Test
    void whenObtenerTodos_thenReturnAllDtos() {
        // given
        Novedad novedad1 = new Novedad();
        Novedad novedad2 = new Novedad();
        NovedadDto dto1 = new NovedadDto();
        NovedadDto dto2 = new NovedadDto();
        given(novedadRepository.findAll()).willReturn(List.of(novedad1, novedad2));
        given(mapper.toNovedadDto(novedad1)).willReturn(dto1);
        given(mapper.toNovedadDto(novedad2)).willReturn(dto2);

        // when
        List<NovedadDto> result = service.obtenerTodos();

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }

    @Test
    void givenDto_whenCrear_thenSaveAndReturn() {
        // given
        NovedadDto dto = new NovedadDto();
        Novedad entity = new Novedad();
        Novedad saved = new Novedad();
        NovedadDto savedDto = new NovedadDto();
        given(mapper.toNovedadEntity(dto)).willReturn(entity);
        given(novedadRepository.save(entity)).willReturn(saved);
        given(mapper.toNovedadDto(saved)).willReturn(savedDto);

        // when
        NovedadDto result = service.crear(dto);

        // then
        assertEquals(savedDto, result);
        then(mapper).should().toNovedadEntity(dto);
        then(novedadRepository).should().save(entity);
        then(mapper).should().toNovedadDto(saved);
    }

    @Test
    void givenIdAndDto_whenActualizar_thenSaveAndReturn() {
        // given
        NovedadDto dto = new NovedadDto();
        Novedad entity = new Novedad();
        NovedadDto updatedDto = new NovedadDto();
        given(novedadRepository.existsById(1L)).willReturn(true);
        given(mapper.toNovedadEntity(dto)).willReturn(entity);
        entity.setId(1L);
        given(novedadRepository.save(entity)).willReturn(entity);
        given(mapper.toNovedadDto(entity)).willReturn(updatedDto);

        // when
        NovedadDto result = service.actualizar(1L, dto);

        // then
        assertEquals(updatedDto, result);
        then(novedadRepository).should().existsById(1L);
        then(mapper).should().toNovedadEntity(dto);
        then(novedadRepository).should().save(entity);
        then(mapper).should().toNovedadDto(entity);
    }

    @Test
    void givenId_whenEliminar_thenDelete() {
        // given
        given(novedadRepository.existsById(1L)).willReturn(true);

        // when
        service.eliminar(1L);

        // then
        then(novedadRepository).should().existsById(1L);
        then(novedadRepository).should().deleteById(1L);
    }

    @Test
    void givenId_whenEliminar_thenThrowNotFound() {
        // given
        given(novedadRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> service.eliminar(1L));
        then(novedadRepository).should().existsById(1L);
        then(novedadRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void whenObtenerNovedadesProfesor_thenReturnOnlyProfesor() {
        // given
        Novedad profesorNovedad = new Novedad();
        profesorNovedad.setTipoDestinatario(TipoDestinatario.PROFESOR);
        NovedadDto dto = new NovedadDto();
        given(novedadRepository.findAll()).willReturn(List.of(profesorNovedad));
        given(mapper.toNovedadDto(profesorNovedad)).willReturn(dto);

        // when
        List<NovedadDto> result = service.obtenerNovedadesProfesor();

        // then
        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void whenCrearNovedadProfesor_thenTipoEsProfesor() {
        // given
        NovedadDto inputDto = new NovedadDto();
        Novedad entity = new Novedad();
        Novedad saved = new Novedad();
        NovedadDto outputDto = new NovedadDto();

        given(mapper.toNovedadEntity(any())).willReturn(entity);
        given(novedadRepository.save(entity)).willReturn(saved);
        given(mapper.toNovedadDto(saved)).willReturn(outputDto);

        // when
        NovedadDto result = service.crearNovedadProfesor(inputDto);

        // then
        assertEquals(outputDto, result);
    }

    @Test
    void whenRegistrarNovedadAutomatica_thenSaveWithAlumnoId() {
        // when
        service.registrarNovedadAutomatica(5L, "Acción", "Detalle");

        // then
        then(novedadRepository).should().save(any(Novedad.class));
    }
}
