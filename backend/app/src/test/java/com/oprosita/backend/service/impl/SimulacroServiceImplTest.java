package com.oprosita.backend.service.impl;

import com.oprosita.backend.dto.ArchivoDto;
import com.oprosita.backend.dto.SimulacroDto;
import com.oprosita.backend.exception.NotFoundException;
import com.oprosita.backend.mapper.SimulacroMapper;
import com.oprosita.backend.model.Archivo;
import com.oprosita.backend.model.Simulacro;
import com.oprosita.backend.repository.ArchivoRepository;
import com.oprosita.backend.repository.SimulacroRepository;
import com.oprosita.backend.service.ArchivoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacroServiceImplTest {

    @Mock private SimulacroRepository simulacroRepository;
    @Mock private ArchivoRepository archivoRepository;
    @Mock private ArchivoService archivoService;
    @Mock private SimulacroMapper mapper;

    @InjectMocks
    private SimulacroServiceImpl simulacroService;

    @Test
    void givenId_whenObtenerPorId_thenReturnDto() {
        // given
        Simulacro simulacro = new Simulacro();
        SimulacroDto dto = new SimulacroDto();
        given(simulacroRepository.findById(1L)).willReturn(Optional.of(simulacro));
        given(mapper.toSimulacroDto(simulacro)).willReturn(dto);

        // when
        SimulacroDto result = simulacroService.obtenerPorId(1L);

        // then
        assertEquals(dto, result);
        then(simulacroRepository).should().findById(1L);
        then(mapper).should().toSimulacroDto(simulacro);
    }

    @Test
    void givenInvalidId_whenObtenerPorId_thenThrowException() {
        // given
        given(simulacroRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> simulacroService.obtenerPorId(1L));
        then(simulacroRepository).should().findById(1L);
    }

    @Test
    void whenObtenerTodos_thenReturnList() {
        // given
        Simulacro s1 = new Simulacro(), s2 = new Simulacro();
        SimulacroDto d1 = new SimulacroDto(), d2 = new SimulacroDto();
        given(simulacroRepository.findAll()).willReturn(List.of(s1, s2));
        given(mapper.toSimulacroDto(s1)).willReturn(d1);
        given(mapper.toSimulacroDto(s2)).willReturn(d2);

        // when
        List<SimulacroDto> result = simulacroService.obtenerTodos();

        // then
        assertEquals(2, result.size());
        then(simulacroRepository).should().findAll();
    }

    @Test
    void givenSimulacroDto_whenCrear_thenReturnSavedDto() {
        // given
        SimulacroDto dto = new SimulacroDto();
        Simulacro entity = new Simulacro();
        given(mapper.toSimulacroEntity(dto)).willReturn(entity);
        given(simulacroRepository.save(entity)).willReturn(entity);
        given(mapper.toSimulacroDto(entity)).willReturn(dto);

        // when
        SimulacroDto result = simulacroService.crear(dto);

        // then
        assertEquals(dto, result);
        then(simulacroRepository).should().save(entity);
    }

    @Test
    void givenValidIdAndDto_whenActualizar_thenReturnUpdatedDto() {
        // given
        Long id = 1L;
        SimulacroDto dto = new SimulacroDto();
        Simulacro entity = new Simulacro();
        entity.setId(id);
        given(simulacroRepository.existsById(id)).willReturn(true);
        given(mapper.toSimulacroEntity(dto)).willReturn(entity);
        given(simulacroRepository.save(entity)).willReturn(entity);
        given(mapper.toSimulacroDto(entity)).willReturn(dto);

        // when
        SimulacroDto result = simulacroService.actualizar(id, dto);

        // then
        assertEquals(dto, result);
        then(simulacroRepository).should().existsById(id);
    }

    @Test
    void givenInvalidId_whenActualizar_thenThrowException() {
        // given
        given(simulacroRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> simulacroService.actualizar(1L, new SimulacroDto()));
        then(simulacroRepository).should().existsById(1L);
    }

    @Test
    void givenValidId_whenEliminar_thenSuccess() {
        // given
        given(simulacroRepository.existsById(1L)).willReturn(true);

        // when
        simulacroService.eliminar(1L);

        // then
        then(simulacroRepository).should().deleteById(1L);
    }

    @Test
    void givenInvalidId_whenEliminar_thenThrowException() {
        // given
        given(simulacroRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(NotFoundException.class, () -> simulacroService.eliminar(1L));
        then(simulacroRepository).should().existsById(1L);
    }

    @Test
    void givenFile_whenCrearSimulacro_thenReturnDto() {
        // given
        MultipartFile file = mock(MultipartFile.class);
        Archivo archivo = new Archivo();
        ArchivoDto archivoDto = new ArchivoDto();
        archivoDto.setId(42);

        Simulacro simulacro = new Simulacro();
        SimulacroDto dto = new SimulacroDto();

        given(file.isEmpty()).willReturn(false);
        given(archivoService.subirArchivo(file)).willReturn(archivoDto);
        given(archivoRepository.findById(42L)).willReturn(Optional.of(archivo));
        given(simulacroRepository.save(any())).willReturn(simulacro);
        given(mapper.toSimulacroDto(simulacro)).willReturn(dto);

        // when
        SimulacroDto result = simulacroService.crearSimulacro("desc", file);

        // then
        assertEquals(dto, result);
        then(file).should().isEmpty();
        then(archivoService).should().subirArchivo(file);
        then(archivoRepository).should().findById(42L);
        then(simulacroRepository).should().save(any(Simulacro.class));
        then(mapper).should().toSimulacroDto(simulacro);
    }
}
